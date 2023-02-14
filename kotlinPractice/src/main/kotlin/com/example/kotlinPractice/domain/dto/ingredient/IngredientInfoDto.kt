package com.example.kotlinPractice.domain.dto.ingredient

import com.example.kotlinPractice.domain.entity.Ingredient
import com.example.kotlinPractice.utils.ModelMapper
import java.time.LocalDate

data class IngredientInfoDto(

        val refrigeratorId: Long,
        val name:String,
        val buyDate: LocalDate,
        val expireDate: LocalDate,
        val expirationPeriod : Int,
        val quantity : Int,
        val priority : Int
) {
    companion object {
        fun of(ingredient: Ingredient?) : IngredientInfoDto{
            return ModelMapper.getMapper()
                    .map(ingredient, IngredientInfoDto::class.java)
        }
    }
}
