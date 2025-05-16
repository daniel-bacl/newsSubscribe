package com.newssubscribe.controller

import com.newssubscribe.service.AuthService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class LoginController(
    private val authService: AuthService
) {
    @GetMapping("/")
    fun loginPage(): String = "login"

    @PostMapping("/send-code")
    @ResponseBody
    fun sendCode(@RequestParam email: String): ResponseEntity<String> {
        val emailRegex = Regex("^(?![.])[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

        if (!email.matches(emailRegex)) {
            return ResponseEntity.badRequest().body("유효하지 않은 이메일 형식입니다.")
        }

        return try {
            authService.sendAuthCode(email)
            ResponseEntity.ok("인증 코드가 전송되었습니다.")
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body("인증 코드 전송 중 오류가 발생했습니다.")
        }
    }

    @PostMapping("/login")
    fun processLogin(
        @RequestParam email: String,
        @RequestParam authCode: String,
        model: Model,
        session: HttpSession
    ): String {
        return if (authService.verifyCode(email, authCode)) {
            session.setAttribute("email", email)
            "redirect:/main"
        } else {
            model.addAttribute("error", "인증 코드가 일치하지 않습니다.")
            "login"
        }
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()  // 모든 세션 데이터 제거
        return "redirect:/"
    }
}
