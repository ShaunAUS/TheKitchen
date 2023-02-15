package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.ingredient.UseIngredientDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto

interface IngredientService {
    fun useIngredient(useIngredientDto: UseIngredientDto): RefrigeratorInfoDto
    fun addIngredient(addIngredientDto: AddIngredientDto) : RefrigeratorInfoDto
}