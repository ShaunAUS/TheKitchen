package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.utils.ModelMapper

data class MemberInfoDto (
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
) {
    //TODO converter
    companion object {
        fun of(member: Member): MemberInfoDto {
            return ModelMapper.getMapper()
                    .map(member, MemberInfoDto::class.java)
        }
    }
}