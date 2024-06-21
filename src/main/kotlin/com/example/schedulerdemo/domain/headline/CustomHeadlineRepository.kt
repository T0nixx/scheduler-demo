package com.example.schedulerdemo.domain.headline

import com.example.schedulerdemo.common.type.Portal

interface CustomHeadlineRepository {
    fun findHeadlines(keyword: String?, portal: Portal?): List<Headline>
}
