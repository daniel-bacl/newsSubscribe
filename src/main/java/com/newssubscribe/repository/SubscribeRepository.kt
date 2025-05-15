package com.newssubscribe.repository

import com.newssubscribe.entity.Keyword
import com.newssubscribe.entity.Subscribe
import com.newssubscribe.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface SubscribeRepository : JpaRepository<Subscribe, Long> {
    fun findByUser(user: User): List<Subscribe>
    fun findByUserAndKeyword(user: User, keyword: Keyword): Subscribe?
}