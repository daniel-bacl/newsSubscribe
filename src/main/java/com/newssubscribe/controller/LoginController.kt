package com.newssubscribe.controller

import com.newssubscribe.service.AuthService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class LoginController(
    private val authService: AuthService
) {
    @GetMapping("/login")
    fun loginPage(): String = "login"

    @PostMapping("/send-code")
    fun sendCode(@RequestParam email: String, model: Model): String {
        if (!email.matches(Regex("^((?!\\.)[\\w\\-.]*[^.])@(\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$"))) {
            model.addAttribute("error", "이메일 형식이 올바르지 않습니다.")
            return "login"
        }
        authService.sendAuthCode(email)
        model.addAttribute("message", "일회용 인증 코드를 전송했습니다. 이메일을 확인하세요.")
        return "login"
    }

    @PostMapping("/login")
    fun processLogin(
        @RequestParam email: String,
        @RequestParam authCode: String,
        model: Model
    ): String {
        return if (authService.verifyCode(email, authCode)) {
            "redirect:/main"
        } else {
            model.addAttribute("error", "인증 코드가 일치하지 않습니다.")
            "login"
        }
    }
}
