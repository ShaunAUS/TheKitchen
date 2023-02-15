package com.example.kotlinPractice.domain.dto.ingredient

data class AddIngredientDto (

        val refrigeratorId: Long,
        val kitchenId :Long,
        val ingredientCreateDtoList: List<IngredientCreateDto>
)