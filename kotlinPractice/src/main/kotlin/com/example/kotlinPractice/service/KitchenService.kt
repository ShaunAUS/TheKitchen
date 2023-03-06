package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto

interface KitchenService {
    fun createKitchen(kitchenCreateDto: KitchenCreateDto): KitchenInfoDto
}