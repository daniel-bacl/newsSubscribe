package com.newssubscribe.repository

import com.newssubscribe.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByEmailAndAuthCode(email: String, authCode: String): User?
}