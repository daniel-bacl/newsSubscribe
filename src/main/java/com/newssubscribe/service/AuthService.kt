package com.newssubscribe.service

import com.newssubscribe.entity.User
import com.newssubscribe.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val mailSenderService: MailSenderService
) {
    fun sendAuthCode(email: String) {
        // 이메일 형식 검증 (이중 체크)
        val emailRegex = Regex("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")
        if (!emailRegex.matches(email)) {
            throw IllegalArgumentException("이메일 형식이 올바르지 않습니다.")
        }

        // 기존 사용자 여부 확인
        val existingUser = userRepository.findByEmail(email)

        // 이미 인증 코드가 존재하면 중복 요청으로 간주
        if (existingUser != null && existingUser.authCode != null) {
            throw IllegalStateException("이미 요청하신 인증 코드가 있습니다. 메일을 확인해 주세요.")
        }

        // 랜덤 8자리 코드 생성
        val code = generateCode()

        val subject = "[NewsSubscribe] 일회용 인증 코드"
        val body = "요청하신 인증 코드는 다음과 같습니다:\n\n$code"
        mailSenderService.send(email, subject, body)

        // DB 저장 (기존 사용자면 update, 아니면 insert)
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
}