package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.utils.ModelMapper


data class MemberWithPrepInfoDto (
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
        val prepList: List<PrepInfoDto>
) {
    //TODO List<Prep>  -> List<PrepInfoDto>
    companion object {
        fun of(member: Member): MemberWithPrepInfoDto {
            return ModelMapper.getMapper()
                    .map(member, MemberWithPrepInfoDto::class.java)
        }
    }
}
