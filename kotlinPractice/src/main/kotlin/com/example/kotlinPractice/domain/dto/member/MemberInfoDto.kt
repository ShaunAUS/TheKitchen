package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType


data class MemberInfoDto(
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
        val kitchen: KitchenInfoDto,
) {

    //TODO converter
    companion object {
        fun of(member: Member):MemberInfoDto {
            return MemberInfoDto(
                    name = member.name,
                    level = LevelType.intToType(member.level),
                    section = SectionType.intToType(member.section),
                    experience = member.experience,
                    kitchen = KitchenInfoDto.of(member.kitchen)
            )
        }

    }
}

