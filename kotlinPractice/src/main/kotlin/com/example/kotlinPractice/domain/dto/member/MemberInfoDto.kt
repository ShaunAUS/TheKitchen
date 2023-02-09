package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor


data class MemberInfoDto (
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
) {
    companion object {
        fun of(member: Member): MemberInfoDto {

        }
    }
}
