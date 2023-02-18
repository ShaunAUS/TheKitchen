package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto

interface PrepService {
    fun createPrepToTargetMember(targetMemberId: Long, prepCreateDtoList: List<PrepCreateDto>): MemberWithPrepInfoDto
    fun updatePrepStatus(prepId: Long): PrepInfoDto
    fun getMyPrep(targetMemberId: Long): MemberWithPrepInfoDto
}