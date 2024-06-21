package com.example.schedulerdemo.api.service

import com.example.schedulerdemo.api.dto.GetHeadlinesRequest
import com.example.schedulerdemo.api.dto.HeadlineResponse
import com.example.schedulerdemo.domain.headline.HeadlineRepository
import org.springframework.stereotype.Service

@Service
class HeadlineService(private val headlineRepository: HeadlineRepository) {
    fun getHeadlines(request: GetHeadlinesRequest): List<HeadlineResponse> {
        val (keyword, portal) = request
        return headlineRepository
            .findHeadlines(keyword, portal)
            .map { HeadlineResponse.from(it) }
    }
}