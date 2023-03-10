package com.example.kotlinPractice.domain.dto.ingredient

data class UseIngredientDto(
        val kitchenId: Long,
        val refrigeratorId: Long,
        val ingredientUseDtos: List<IngredientUseDto>
)
