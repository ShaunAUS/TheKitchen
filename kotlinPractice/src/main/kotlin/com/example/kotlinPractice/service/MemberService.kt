package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberService {
    fun createMember(memberCreateDto: MemberCreateDto, kitchenId: Long): MemberInfoDto
    fun getMembers(pageable: Pageable): Page<MemberInfoDto>
    fun updateMember(targetMemberId: Long, updateDto: MemberUpdateDto): MemberInfoDto
    fun removeMember(targetMemberId: Long)
    fun getMember(targetMemberId: Long): MemberInfoDto
}