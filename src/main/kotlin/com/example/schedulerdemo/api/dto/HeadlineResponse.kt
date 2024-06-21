package com.example.schedulerdemo.api.dto

import com.example.schedulerdemo.common.type.Portal
import com.example.schedulerdemo.domain.headline.Headline
import java.time.LocalDateTime

data class HeadlineResponse(
    val title: String,
    val publisher: String,
    val portal: Portal,
    val publishedAt: LocalDateTime,
    val url: String,
    val id: Long,
) {
    companion object {
        fun from(headline: Headline): HeadlineResponse {
            return HeadlineResponse(
                id = headline.id!!,
                url = headline.url,
                publishedAt = headline.publishedAt,
                publisher = headline.publisher,
                portal = headline.portal,
                title = headline.title
            )
        }
    }
}


