package com.newssubscribe.controller

import org.springframework.ui.Model
import com.newssubscribe.service.KeywordService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class KeywordController(
    private val keywordService: KeywordService
) {
    @GetMapping("/main")
    fun mainPage(session: HttpSession, model: Model): String {
        val email = session.getAttribute("email") as? String
            ?: return "redirect:/"

        model.addAttribute("email", email)

        val keywords = keywordService.getKeywordsByEmail(email)
        model.addAttribute("keywords", keywords)

        val sendHour = keywordService.getSendHourByEmail(email)
        model.addAttribute("sendHour", sendHour)

        return "main"
    }

    @PostMapping("/keywords/add")
    fun addKeyword(
        @RequestParam keyword: String,
        session: HttpSession,
        redirect: RedirectAttributes
    ): String {
        val email = session.getAttribute("email") as? String
            ?: return "redirect:/"

        try {
            keywordService.addKeyword(email, keyword)
            redirect.addFlashAttribute("message", "키워드가 추가되었습니다.")
        } catch (e: IllegalArgumentException) {
            redirect.addFlashAttribute("error", e.message)
        }

        return "redirect:/main"
    }

    @PostMapping("/keywords/delete")
    fun deleteKeyword(
        @RequestParam keywordId: Long,
        session: HttpSession,
        redirect: RedirectAttributes
    ): String {
        val email = session.getAttribute("email") as? String
            ?: return "redirect:/"

        keywordService.deleteKeyword(email, keywordId)
        redirect.addFlashAttribute("message", "키워드가 삭제되었습니다.")
        return "redirect:/main"
    }

    @PostMapping("/send-hour")
    fun updateSendHour(
        @RequestParam sendHour: String,
        session: HttpSession,
        redirect: RedirectAttributes
    ): String {
        val email = session.getAttribute("email") as? String ?: return "redirect:/"

        keywordService.updateSendHour(email, sendHour)
        redirect.addFlashAttribute("message", "발송 시간이 저장되었습니다.")
        return "redirect:/main"
    }
}
