package com.newssubscribe.entity

import jakarta.persistence.*

@Entity
@Table(name = "keyword")
class Keyword(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "keyword", nullable = false, unique = true)
    var name: String = "",

    var subscribeCount: Int = 0
)
