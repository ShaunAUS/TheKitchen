package com.example.kotlinPractice.domain.repository.querydsl

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Prep

interface PrepCustomRepository {
    fun findByMemberId(targetMemberId: Long): List<Prep>

}