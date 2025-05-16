package com.newssubscribe.service

import com.newssubscribe.entity.User
import com.newssubscribe.repository.UserRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val mailSender: JavaMailSender
) {
    fun sendAuthCode(email: String) {
        // 이메일 형식 검증 (이중 체크)
        val emailRegex = Regex("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")
        if (!emailRegex.matches(email)) {
            throw IllegalArgumentException("이메일 형식이 올바르지 않습니다.")
        }

        // 랜덤 8자리 코드 생성
        val code = generateCode()

        // 이메일 전송
        sendMail(email, code)

        // DB 저장 (기존 사용자면 update, 아니면 insert)
        val existingUser = userRepository.findByEmail(email)
        if (existingUser != null) {
            existingUser.authCode = code
            existingUser.createdAt = LocalDateTime.now()
            userRepository.save(existingUser)
        } else {
            val newUser = User(email = email, authCode = code)
            userRepository.save(newUser)
        }
    }

    fun verifyCode(email: String, authCode: String): Boolean {
        val user = userRepository.findByEmailAndAuthCode(email, authCode)

        if (user != null) {
            // 인증 성공 → 인증코드 초기화
            user.authCode = null
            userRepository.save(user)  // 변경 사항 저장
            return true
        }
        return false
    }

    private fun generateCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..8)
            .map { chars.random() }
            .joinToString("")
    }

    private fun sendMail(to: String, code: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = "[NewsSubscribe] 일회용 인증 코드"
        message.text = "요청하신 인증 코드는 다음과 같습니다:\n\n$code"
        mailSender.send(message)
    }
}