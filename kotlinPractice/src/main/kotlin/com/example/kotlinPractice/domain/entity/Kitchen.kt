package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import jakarta.persistence.*

@Entity
class Kitchen(

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val location: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
) {
        companion object {
            fun of(createKitchenDto: KitchenCreateDto): Kitchen {
                return Kitchen(
                        name = createKitchenDto.name,
                        location = createKitchenDto.location,
                        id = null
                )
            }

            }
        }
}