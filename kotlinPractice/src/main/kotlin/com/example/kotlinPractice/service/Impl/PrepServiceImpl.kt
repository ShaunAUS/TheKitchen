package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.entity.Prep
import com.example.kotlinPractice.domain.repository.MemberRepository
import com.example.kotlinPractice.domain.repository.PrepRepository
import com.example.kotlinPractice.service.PrepService
import com.group.libraryapp.utils.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class PrepServiceImpl(
        private val prepRepository: PrepRepository,
        private val memberRepository: MemberRepository
) : PrepService {


    //다른 맴버에게 주는 형식
    override fun createPrepToTargetMember(targetMemberId: Long, prepCreateDtos: List<PrepCreateDto>): MemberWithPrepInfoDto {

        val prepList = mutableListOf<Prep>()
        val targetMember = getMemberOrThrow(targetMemberId)

        for (prepCreateDto in prepCreateDtos) {
            prepList.add(Prep.of(prepCreateDto,targetMember))
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

    //TODO  querydsl or only JPA?
    /*override fun getMyPrep(memberId: Long): MemberWithPrepInfoDto {
        return MemberWithPrepInfoDto.of(getMemberWithPreps(memberId))
    }*/

    private fun getMemberOrThrow(memberId: Long): Member {
        return memberRepository.findByIdOrThrow(memberId)
    }

}