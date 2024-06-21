package com.example.schedulerdemo.client

import com.example.schedulerdemo.common.type.Portal
import com.example.schedulerdemo.domain.headline.Headline

interface HeadlineClient {
    fun getHeadlines(): List<Headline>
    fun supports(portal: Portal): Boolean
}