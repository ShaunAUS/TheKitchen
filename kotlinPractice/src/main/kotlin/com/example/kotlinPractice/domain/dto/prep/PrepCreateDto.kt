package com.example.kotlinPractice.domain.dto.prep

import com.example.kotlinPractice.domain.enums.ExecutionType
import java.time.LocalDate
import java.time.LocalDateTime

data class PrepCreateDto(

        val job:String,
        val priority:Int,
        val executionType: ExecutionType,
        val executionDate: LocalDate

) {
}