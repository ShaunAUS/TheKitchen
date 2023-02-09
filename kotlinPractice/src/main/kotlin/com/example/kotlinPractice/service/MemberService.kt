package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberService {
    fun register(memberCreateDto: MemberCreateDto): MemberInfoDto
    fun getMembers(pageable: Pageable): Page<MemberInfoDto>
    fun updateMember(memberNo: Long,updateDto: MemberUpdateDto): MemberInfoDto
    fun removeMember(memberNo: Long)
    fun getMember(memberNo: Long): MemberInfoDto
}