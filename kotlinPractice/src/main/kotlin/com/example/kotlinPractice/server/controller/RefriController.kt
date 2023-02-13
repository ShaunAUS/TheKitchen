package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.service.IngredientService
import com.example.kotlinPractice.service.RefrigeratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RefriController(

        private val refrigeratorService: RefrigeratorService,
) {

    @GetMapping("/{refriId}")
    fun getRefrigeratorStatus(
            @PathVariable refriId: Long,
    ): RefrigeratorInfoDto {
        return refrigeratorService.getRefrigerator(refriId)

    }
    //재료구매
    @PostMapping("/{refriId}")
    fun buyIngredient(
            @PathVariable refriId: Long,
    ): RefrigeratorInfoDto {
        return refrigeratorService.addIngredient(refriId)
    }

    @PatchMapping("/{refriId}/{ingredientId}")
    fun useIngredient(
            @PathVariable refriId: Long,
            @PathVariable ingredientId: Long,
    ): IngredientInfoDto {
        return refrigeratorService.useIngredient(refriId, ingredientId)
    }

}