package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto

interface RefrigeratorService {
    fun useIngredient(refrigeratorId: Long, useIngredients: List<IngredientUseDto>): RefrigeratorInfoDto
    fun addIngredient(refrigeratorId: Long, refrigeratorCreateDto: RefrigeratorCreateDto): RefrigeratorInfoDto
    fun getRefrigerator(refrigeratorId: Long): RefrigeratorInfoDto
}