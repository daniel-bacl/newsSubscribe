package com.newssubscribe.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailSenderService(private val mailSender: JavaMailSender) {
    fun send(to: String, subject: String, text: String) {
        val message = mailSender.createMimeMessage()
        MimeMessageHelper(message, true).apply {
            setFrom("noreply@sol-dni.click")
            setTo(to)
            setSubject(subject)
            setText(text, false)
        }
        try {
            mailSender.send(message)
        } catch (e: Exception) {
            throw RuntimeException("메일 전송 실패: ${e.message}", e)
        }

    }
}