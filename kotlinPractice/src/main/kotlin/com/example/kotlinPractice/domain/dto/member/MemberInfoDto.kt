package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.entity.Kitchen
import com.example.kotlinPractice.domain.entity.Member
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.utils.ModelMapper


data class MemberInfoDto(
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
        val kitchen: Kitchen,
) {

    //TODO converter
    companion object {
        fun of(member: Member):MemberInfoDto {
         return   ModelMapper.getMapper()
                    .typeMap(Member::class.java, MemberInfoDto::class.java)
                    .addMappings { mapper ->
                        mapper.using(LevelType.INT_TO_LEVELTYPE_CONVERTER())
                                .map(Member::level,MemberInfoDto::level)
                        mapper.using(SectionType.INT_TO_SECTIONTYPE_CONVERTER())
                                .map(Member::section,MemberInfoDto::section)
                    }
                    .map(member)
        }

    }
}

