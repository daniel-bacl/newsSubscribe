package com.newsSubscribe.repository

import com.newsSubscribe.entity.Keyword
import com.newsSubscribe.entity.Subscribe
import com.newsSubscribe.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface SubscribeRepository : JpaRepository<Subscribe, Long> {
    fun findByUser(user: User): List<Subscribe>
    fun findByUserAndKeyword(user: User, keyword: Keyword): Subscribe?
}