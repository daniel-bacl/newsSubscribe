package com.newssubscribe.service

import com.newssubscribe.entity.Keyword
import com.newssubscribe.entity.Subscribe
import com.newssubscribe.repository.KeywordRepository
import com.newssubscribe.repository.SubscribeRepository
import com.newssubscribe.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val userRepository: UserRepository,
    private val keywordRepository: KeywordRepository,
    private val subscribeRepository: SubscribeRepository
) {

    // 1. 사용자 이메일로 구독한 키워드 목록 조회
    fun getKeywordsByEmail(email: String): List<Keyword> {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        val subscribes = subscribeRepository.findByUser(user)
        return subscribes.map { it.keyword }
    }

    // 2. 키워드 추가
    fun addKeyword(email: String, keywordName: String) {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        val keyword = keywordRepository.findByName(keywordName)
            ?: keywordRepository.save(Keyword(name = keywordName, subscribeCount = 0))

        // 이미 구독 중인지 확인
        val existing = subscribeRepository.findByUserAndKeyword(user, keyword)
        if (existing != null) return // 중복 방지

        // 구독 관계 저장
        keyword.subscribeCount++
        keywordRepository.save(keyword)

        val subscribe = Subscribe(user = user, keyword = keyword)
        subscribeRepository.save(subscribe)
    }

    // 3. 키워드 삭제
    fun deleteKeyword(email: String, keywordId: Long) {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        val keyword = keywordRepository.findById(keywordId)
            .orElseThrow { IllegalArgumentException("키워드를 찾을 수 없습니다.") }

        val subscribe = subscribeRepository.findByUserAndKeyword(user, keyword)
            ?: return // 구독 중이 아니면 무시

        subscribeRepository.delete(subscribe)

        keyword.subscribeCount = (keyword.subscribeCount - 1).coerceAtLeast(0)
        keywordRepository.save(keyword)
    }

    // 사용자 발송 시간 조회
    fun getSendHourByEmail(email: String): String? {
        return userRepository.findByEmail(email)?.sendHour
    }

    // 사용자 발송 시간 저장
    fun updateSendHour(email: String, sendHour: String) {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다.")

        user.sendHour = sendHour
        userRepository.save(user)
    }
}
