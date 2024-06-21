package com.example.schedulerdemo.domain.headline

import com.example.schedulerdemo.common.type.Portal
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Headline(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "url", nullable = false)
    val url: String,

    @Column(name = "publisher", nullable = false)
    val publisher: String,

    @Column(name = "portal", nullable = false)
    @Enumerated(EnumType.STRING)
    val portal: Portal,

    @Column(name = "published_at", nullable = false)
    val publishedAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}