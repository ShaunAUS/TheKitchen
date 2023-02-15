package com.example.kotlinPractice.domain.dto.prep

import com.example.kotlinPractice.domain.enums.ExecutionType
import java.time.LocalDate

data class PrepCreateDto(

        val job:String,
        val priority:Int,
        val executionStatus: ExecutionType,
        val executionDate:LocalDate

) {
}