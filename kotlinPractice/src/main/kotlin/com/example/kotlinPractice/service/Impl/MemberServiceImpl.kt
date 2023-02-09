package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.repository.MemberRepository
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.MemberService
import com.group.libraryapp.utils.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
@Slf4j
class MemberServiceImpl(

        private val memberRepository: MemberRepository,

        ) : MemberService {
    override fun register(memberCreateDto: MemberCreateDto): MemberInfoDto {
        val member = Member.of(memberCreateDto)
        memberRepository.save(member)
        return MemberInfoDto.of(member)
    }

    override fun getMember(memberNo: Long): MemberInfoDto {
        return MemberInfoDto.of(memberRepository.findByIdOrThrow(memberNo))
    }

    override fun getMembers(pageable: Pageable): Page<MemberInfoDto> {
        return memberRepository.findAll(pageable)
                .map { member -> MemberInfoDto.of(member) }
    }

    @Transactional
    override fun updateMember(memberNo: Long, updateDto: MemberUpdateDto): MemberInfoDto {
        val memberById = memberRepository.findByIdOrThrow(memberNo)

        memberById.update(updateDto)

        return MemberInfoDto.of(memberById)

    }

    override fun removeMember(memberNo: Long) {
        memberRepository.deleteById(memberNo)
    }


}