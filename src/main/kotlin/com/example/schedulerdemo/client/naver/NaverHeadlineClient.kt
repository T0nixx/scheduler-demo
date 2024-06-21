package com.example.schedulerdemo.client.naver

import com.example.schedulerdemo.client.HeadlineClient
import com.example.schedulerdemo.common.type.Portal
import com.example.schedulerdemo.domain.headline.Headline
import it.skrape.core.htmlDocument
import it.skrape.matchers.toBePresent
import it.skrape.selects.html5.a
import it.skrape.selects.html5.b
import it.skrape.selects.html5.div
import it.skrape.selects.html5.strong
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class NaverHeadlineClient(private val restClient: RestClient) : HeadlineClient {
    companion object {
        private const val NAVER_IT_HEADLINES_URL =
            "https://news.naver.com/breakingnews/section/105/230"
    }

    override fun getHeadlines(): List<Headline> {
        val document = restClient
            .get()
            .uri("https://news.naver.com/breakingnews/section/105/230")
            .retrieve()
            .body<String>() ?: throw RuntimeException()

        return htmlDocument(document) {
            div {
                withClass = "sa_text"
                findAll {
                    toBePresent
                }.map {
                    Headline(
                        portal = Portal.NAVER,
                        publishedAt = calculatePublishedAt(
                            it.div {
                                withClass = "sa_text_datetime"
                                findFirst {
                                    b {
                                        findFirst {
                                            text
                                        }
                                    }
                                }
                            }
                        ),
                        publisher = it.div {
                            withClass = "sa_text_press"
                            findFirst {
                                text
                            }
                        },
                        title = it.strong {
                            withClass = "sa_text_strong"
                            findFirst {
                                text
                            }
                        },
                        url = it.a {
                            withClass = "sa_text_title"
                            findFirst {
                                eachHref.first()
                            }
                        }
                    )
                }
            }
        }
    }

    override fun supports(portal: Portal): Boolean {
        return portal == Portal.NAVER
    }

    private fun calculatePublishedAt(timeDiff: String): LocalDateTime {
        if (timeDiff.endsWith("분전")) {
            return timeDiff.split("분전")[0].toLong().let {
                LocalDateTime
                    .now()
                    .minusMinutes(it)
                    .truncatedTo(ChronoUnit.MINUTES)
            }
        }
        if (timeDiff.endsWith("시간전")) {
            return timeDiff.split("시간전")[0].toLong().let {
                LocalDateTime
                    .now()
                    .minusHours(it)
                    .truncatedTo(ChronoUnit.MINUTES)
            }
        }
        throw RuntimeException()
    }
}