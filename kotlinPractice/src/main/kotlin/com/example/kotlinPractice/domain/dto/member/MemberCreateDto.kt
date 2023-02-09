package com.example.kotlinPractice.domain.dto.member

import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import jakarta.persistence.Column

data class MemberCreateDto(
        val name: String,
        val level: LevelType,
        val section: SectionType,
        val experience: Int?,
        )
