package com.newssubscribe.entity

import jakarta.persistence.*

@Entity
@Table(name = "keyword")
class Keyword(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, unique = true)
    var name: String = "",

    var subscribeCount: Int = 0
)
