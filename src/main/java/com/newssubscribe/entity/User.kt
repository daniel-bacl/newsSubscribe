package com.newssubscribe.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = true)
    var authCode: String? = null,

    @Column(name = "send_hour", nullable = true)
    var sendHour: String? = null,

    var createdAt: LocalDateTime = LocalDateTime.now()
)