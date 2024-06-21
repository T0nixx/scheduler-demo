package com.example.schedulerdemo.client

import com.example.schedulerdemo.common.type.Portal
import com.example.schedulerdemo.domain.headline.Headline
import com.example.schedulerdemo.domain.headline.HeadlineRepository
import jakarta.transaction.NotSupportedException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@EnableScheduling
class HeadlineClientService(
    private val clients: List<HeadlineClient>,
    private val headlineRepository: HeadlineRepository,
) {
    companion object {
        private const val DEFAULT_TASK_RATE = 120000L
    }

    private val logger =
        LoggerFactory.getLogger(HeadlineClientService::class.java)

    @Transactional
    @Scheduled(fixedRate = DEFAULT_TASK_RATE)
    fun performTask() {
        logger.info("Task performed at: ${LocalDateTime.now()}")
        val existingUrls =
            headlineRepository
                .findTop100ByOrderByPublishedAt()
                .map { it.url }
                .toSet()
        Portal.entries.fold(mutableListOf<Headline>()) { acc, portal ->
            acc.also {
                it.addAll(
                    this.getHeadlines(portal)
                )
            }
        }.filter {
            existingUrls.contains(it.url) == false
        }.let {
            headlineRepository.saveAll(it)
        }
    }

    private fun getHeadlines(portal: Portal): List<Headline> {
        return this.selectClient(portal).getHeadlines()
    }

    private fun selectClient(portal: Portal): HeadlineClient {
        return clients.find { it.supports(portal) }
            ?: throw NotSupportedException("지원하지 않는 Portal 입니다.")
    }
}