package com.example.kotlinPractice.domain.dto.kitchen

import com.example.kotlinPractice.domain.entity.Kitchen

data class KitchenInfoDto(
        val name: String,
        val location : String
) {
    companion object {
        fun of(kitchen: Kitchen): KitchenInfoDto {
            return KitchenInfoDto(
                    name = kitchen.name,
                    location = kitchen.location
            )
        }
    }
}