package com.example.kotlinPractice.domain.dto.ingredient

data class AddIngredientDto (

        val kitchenId :Long,
        val refrigeratorId: Long,
        val ingredientCreateDtoList: List<IngredientCreateDto>
)