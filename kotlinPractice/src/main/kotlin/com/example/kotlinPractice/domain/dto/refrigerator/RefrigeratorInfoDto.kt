package com.example.kotlinPractice.domain.dto.refrigerator


import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.entity.Refrigerator

data class RefrigeratorInfoDto(

        val id: Long?,
        val name: String,
        val ingredients: List<IngredientInfoDto>,

        ) {
    companion object {
        fun of(refrigerator: Refrigerator): RefrigeratorInfoDto {
            return RefrigeratorInfoDto(
                    id = refrigerator.id,
                    name =refrigerator.name,
                    ingredients = refrigerator.ingredients.map { ingredient -> IngredientInfoDto.of(ingredient) }
            )
        }
    }
}