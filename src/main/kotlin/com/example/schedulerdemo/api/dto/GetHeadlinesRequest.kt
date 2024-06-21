package com.example.schedulerdemo.api.dto

import com.example.schedulerdemo.common.type.Portal

data class GetHeadlinesRequest(
    val keyword: String?,
    val portal: Portal?,
)
