package com.newsSubscribe.entity

import jakarta.persistence.*

@Entity
@Table(name = "subscribe")
class Subscribe(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    var keyword: Keyword
)
