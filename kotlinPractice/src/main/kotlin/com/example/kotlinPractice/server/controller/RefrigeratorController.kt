package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.service.RefrigeratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RefrigeratorController(

        private val refrigeratorService: RefrigeratorService,
) {

    @GetMapping("/{refrigeratorId}")
    fun getRefrigeratorStatus(
            @PathVariable refrigeratorId: Long,
    ): RefrigeratorInfoDto {
        return refrigeratorService.getRefrigerator(refrigeratorId)

    }

    @PostMapping("/{refrigeratorId}")
    fun buyIngredient(
            @PathVariable refrigeratorId: Long,
            @RequestBody refrigeratorCreateDto: RefrigeratorCreateDto
    ): RefrigeratorInfoDto {

        return refrigeratorService.addIngredient(refrigeratorId,refrigeratorCreateDto)
    }

    @PatchMapping("/{refrigeratorId}")
    fun useIngredient(
            @PathVariable refrigeratorId: Long,
            @RequestBody useIngredients: List<IngredientUseDto>,
    ): RefrigeratorInfoDto {
        return refrigeratorService.useIngredient(refrigeratorId, useIngredients)
    }

}