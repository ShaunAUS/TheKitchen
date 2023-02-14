package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.entity.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository:JpaRepository<Ingredient,Long> {
    fun findByNameAndRefrigeratorId(name: String, refrigeratorId: Long): Ingredient?

}