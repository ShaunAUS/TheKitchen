package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.entity.Prep
import com.example.kotlinPractice.domain.repository.MemberRepository
import com.example.kotlinPractice.domain.repository.PrepRepository
import com.example.kotlinPractice.service.MemberService
import com.group.libraryapp.utils.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class MemberServiceImpl(

        private val memberRepository: MemberRepository,
        private val prepRepository: PrepRepository,

        ) : MemberService {
    override fun register(memberCreateDto: MemberCreateDto): MemberWithPrepInfoDto {
        val member = Member.of(memberCreateDto)
        memberRepository.save(member)
        return MemberWithPrepInfoDto.of(member)
    }

    override fun getMember(memberId: Long): MemberWithPrepInfoDto {
        return MemberWithPrepInfoDto.of(getMemberOrThrow(memberId))
    }

    //다른 맴버에게 주는 형식
    override fun makePrep(targetMemberId: Long, prepCreateDtoList: List<PrepCreateDto>): MemberWithPrepInfoDto {

        val prepList = mutableListOf<Prep>()
        val targetMember = getMemberOrThrow(targetMemberId)

        for (prepCreateDto in prepCreateDtoList) {
            prepList.add(Prep.of(prepCreateDto).updateExecutionMember(targetMember))
        }
        prepRepository.saveAll(prepList)

        return MemberWithPrepInfoDto.of(targetMember)
    }

    @Transactional
    override fun updatePrepStatus(prepId: Long): PrepInfoDto {
        val prepById = prepRepository.findByIdOrThrow(prepId)
        prepById.updatePrepStatus()

        return PrepInfoDto.of(prepById)

    }

    //TODO need querydsl
    override fun getMyPrep(memberId: Long) : MemberWithPrepInfoDto{
        return MemberWithPrepInfoDto.of(getMemberWithPreps(memberId))
    }


    override fun getMembers(pageable: Pageable): Page<MemberWithPrepInfoDto> {
        return memberRepository.findAll(pageable)
                .map { member -> MemberWithPrepInfoDto.of(member) }
    }

    @Transactional
    override fun updateMember(memberId: Long, updateDto: MemberUpdateDto): MemberWithPrepInfoDto {
        val targetMember = getMemberOrThrow(memberId)

        targetMember.update(updateDto)

        return MemberWithPrepInfoDto.of(targetMember)

    }

    override fun removeMember(memberNo: Long) {
        memberRepository.deleteById(memberNo)
    }

    private fun getMemberOrThrow(memberId: Long): Member {
        return memberRepository.findByIdOrThrow(memberId)
    }


}