package com.newssubscribe.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class MailSenderService(private val mailSender: JavaMailSender) {
    fun send(to: String, subject: String, text: String) {
        val message = mailSender.createMimeMessage()
        MimeMessageHelper(message, true).apply {
            setTo(to)
            setSubject(subject)
            setText(text, false)
        }
        mailSender.send(message)
    }
}