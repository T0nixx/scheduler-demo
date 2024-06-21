package com.example.schedulerdemo.api.converter

import com.example.schedulerdemo.common.type.Portal
import org.springframework.core.convert.converter.Converter

class PortalConverter : Converter<String, Portal> {

    override fun convert(source: String): Portal {
        return runCatching {
            Portal.valueOf(source.uppercase())
        }.getOrElse {
            throw IllegalArgumentException()
        }
    }
}
