package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto

interface RefrigeratorService {
    fun useIngredient(refriId: Long, ingredientId: Long): IngredientInfoDto
    fun addIngredient(refriId: Long): RefrigeratorInfoDto
    fun getRefrigerator(refriId: Long): RefrigeratorInfoDto
}