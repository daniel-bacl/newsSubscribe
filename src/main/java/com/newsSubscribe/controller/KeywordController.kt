package com.newsSubscribe.controller

import org.springframework.ui.Model
import com.newsSubscribe.service.KeywordService
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
    fun mainPage(@RequestParam email: String, model: Model): String {
        val keywords = keywordService.getKeywordsByEmail(email)
        model.addAttribute("keywords", keywords)
        return "main"
    }

    @PostMapping("/keywords/add")
    fun addKeyword(
        @RequestParam email: String,
        @RequestParam keyword: String,
        redirect: RedirectAttributes
    ): String {
        keywordService.addKeyword(email, keyword)
        redirect.addFlashAttribute("message", "키워드가 추가되었습니다.")
        return "redirect:/main?email=$email"
    }

    @PostMapping("/keywords/delete")
    fun deleteKeyword(
        @RequestParam email: String,
        @RequestParam keywordId: Long,
        redirect: RedirectAttributes
    ): String {
        keywordService.deleteKeyword(email, keywordId)
        redirect.addFlashAttribute("message", "키워드가 삭제되었습니다.")
        return "redirect:/main?email=$email"
    }
}
