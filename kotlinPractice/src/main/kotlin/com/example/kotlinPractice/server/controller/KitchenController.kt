package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.service.KitchenService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KitchenController(

        val kitchenService : KitchenService
){

    @PostMapping("")
    fun createKitchen(
            @RequestBody createKitchenDto : KitchenCreateDto
    ): KitchenInfoDto {

        return kitchenService.createKitchen(createKitchenDto)
    }

}