package com.example.kotlinPractice.domain.repository.querydsl.Impl

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Prep
import com.example.kotlinPractice.domain.entity.QPrep.prep
import com.example.kotlinPractice.domain.repository.querydsl.PrepCustomRepository
import com.querydsl.core.Tuple
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Repository

@Repository
class PrepCustomRepositoryImpl(
        private val queryFactory: JPAQueryFactory,
): PrepCustomRepository{
    override fun findByMemberId(targetMemberId: Long): List<Prep> {
        return queryFactory
                .select(prep)
                .from(prep)
                .where(prep.member.id.eq(targetMemberId))
                .fetch()
    }


}