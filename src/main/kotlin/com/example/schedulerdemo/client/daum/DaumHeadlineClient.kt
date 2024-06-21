package com.example.schedulerdemo.client.daum

import com.example.schedulerdemo.client.HeadlineClient
import com.example.schedulerdemo.client.config.getPublishedAt
import com.example.schedulerdemo.common.type.Portal
import com.example.schedulerdemo.domain.headline.Headline
import it.skrape.core.htmlDocument
import it.skrape.selects.html5.a
import it.skrape.selects.html5.span
import it.skrape.selects.html5.ul
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class DaumHeadlineClient(private val restClient: RestClient) : HeadlineClient {
    override fun getHeadlines(): List<Headline> {
        val document =
            restClient
                .get()
                // rate 안에 1페이지 이상의 기사가 올라올 경우 누락되는 기사 생길 수 있음
                .uri("https://news.daum.net/breakingnews/digital")
                .retrieve()
                .body<String>() ?: throw RuntimeException()

        return htmlDocument(document) {
            ul {
                withClass = "list_news2"
                findFirst {
                    children {
                        map {
                            Headline(
                                title = it.a {
                                    withClass = "link_txt"
                                    findFirst { text }
                                },
                                portal = Portal.DAUM,
                                url = it.a {
                                    withClass = "link_txt"
                                    findFirst { eachHref.first() }
                                },
                                publishedAt = getPublishedAt(
                                    it.span {
                                        withClass = "info_time"
                                        findFirst {
                                            text
                                        }
                                    }
                                ),
                                publisher = it.span {
                                    withClass = "info_news"
                                    findFirst { text }
                                }.split(" · ")[0]
                            )
                        }
                    }
                }
            }
        }
    }

    override fun supports(portal: Portal): Boolean {
        return portal == Portal.DAUM
    }
}