package com.example.kotlinPractice.domain.dto.ingredient

import java.time.LocalDate

data class IngredientInfoDto(

        val refrigeratorId: Long,
        val name:String,
        val buyDate: LocalDate,
        val expireDate: LocalDate,
        val expirationPeriod : Int,
        val quantity : Int,
        val priority : Int
)
