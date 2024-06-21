package com.example.schedulerdemo.domain.headline

import com.example.schedulerdemo.common.type.Portal
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory

class CustomHeadlineRepositoryImpl(private val queryFactory: JPAQueryFactory) :
    CustomHeadlineRepository {
    private val headline = QHeadline.headline
    override fun findHeadlines(
        keyword: String?,
        portal: Portal?,
    ): List<Headline> {
        return queryFactory
            .selectFrom(headline)
            .where(titleLike(keyword))
            .where(portalEquals(portal))
            .orderBy(headline.publishedAt.desc())
            .fetch()
    }

    private fun titleLike(keyword: String?): BooleanExpression? {
        return keyword?.let { headline.title.like(it) }
    }

    private fun portalEquals(portal: Portal?): BooleanExpression? {
        return portal?.let { headline.portal.eq(it) }
    }
}