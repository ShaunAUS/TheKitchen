package com.example.kotlinPractice.domain.dto.prep

import java.time.LocalDate

data class PrepCreateDto(

        val job:String,
        val priority:Int,
        val executionStatus:Boolean,
        val executionDate:LocalDate

) {
}