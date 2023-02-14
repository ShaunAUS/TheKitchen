package com.example.kotlinPractice.domain.dto.refrigerator

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import lombok.Builder

data class RefrigeratorCreateDto(

        val name: String,
        val ingredients : List<IngredientCreateDto>
) {
}