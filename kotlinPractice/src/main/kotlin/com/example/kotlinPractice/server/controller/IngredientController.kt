package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.ingredient.UseIngredientDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.service.IngredientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/ingredient")
class IngredientController(

        private val ingredientService: IngredientService,
) {

    @PostMapping("")
    fun buyIngredient(
            @RequestBody addIngredientDto: AddIngredientDto,
    ): RefrigeratorInfoDto {

        return ingredientService.addIngredient(addIngredientDto)
    }

    @PatchMapping("")
    fun useIngredient(
            @RequestBody useIngredientDto: UseIngredientDto,
    ): RefrigeratorInfoDto {
        return ingredientService.useIngredient(useIngredientDto)
    }

}