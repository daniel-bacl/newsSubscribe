package com.newsSubscribe.repository

import com.newsSubscribe.entity.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByName(name: String): Keyword?
}