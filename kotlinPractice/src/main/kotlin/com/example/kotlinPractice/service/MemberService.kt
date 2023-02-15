package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface MemberService {
    fun register(memberCreateDto: MemberCreateDto): MemberWithPrepInfoDto
    fun getMembers(pageable: Pageable): Page<MemberWithPrepInfoDto>
    fun updateMember(memberNo: Long,updateDto: MemberUpdateDto): MemberWithPrepInfoDto
    fun removeMember(memberNo: Long)
    fun getMember(memberNo: Long): MemberWithPrepInfoDto
    fun makePrep(targetMemberId: Long, prepCreateDtoList: List<PrepCreateDto>): MemberWithPrepInfoDto
    fun updatePrepStatus(prepId: Long): PrepInfoDto
    fun getMyPrep(memberId: Long) : MemberWithPrepInfoDto

    fun getMemberWithPreps(memberId: Long): Member
}