package com.example.schedulerdemo.api.controller

import com.example.schedulerdemo.api.dto.GetHeadlinesRequest
import com.example.schedulerdemo.api.dto.HeadlineResponse
import com.example.schedulerdemo.api.service.HeadlineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api")
class HeadlineController(private val headlineService: HeadlineService) {
    @GetMapping("/headlines")
    fun getHeadlines(getHeadlinesRequest: GetHeadlinesRequest): ResponseEntity<List<HeadlineResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(headlineService.getHeadlines(getHeadlinesRequest))
    }
}