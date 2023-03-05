package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.domain.entity.Kitchen
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.service.KitchenService

class KitchenServiceImpl(
        private val kitchenRepository: KitchenRepository,
) : KitchenService {
    override fun createKitchen(createKitchenDto: KitchenCreateDto): KitchenInfoDto {
        val savedKitchen = kitchenRepository.save(Kitchen.of(createKitchenDto))
        return KitchenInfoDto.of(savedKitchen)
    }
}