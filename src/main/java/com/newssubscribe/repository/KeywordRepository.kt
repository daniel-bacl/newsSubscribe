package com.newssubscribe.repository

import com.newssubscribe.entity.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByName(name: String): Keyword?
}