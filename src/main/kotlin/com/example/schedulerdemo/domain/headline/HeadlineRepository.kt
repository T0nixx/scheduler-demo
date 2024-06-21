package com.example.schedulerdemo.domain.headline

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HeadlineRepository : JpaRepository<Headline, Long>,
    CustomHeadlineRepository {
    fun findTop100ByOrderByPublishedAt(): List<Headline>
}

