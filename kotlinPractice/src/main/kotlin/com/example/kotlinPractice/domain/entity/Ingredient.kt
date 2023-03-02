package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.utils.ModelMapper
import com.group.libraryapp.utils.empty
import jakarta.persistence.*
import org.modelmapper.Converter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

@Entity
class Ingredient(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val buyDate: LocalDate,

        @Column(nullable = false)
        val expireDate: LocalDate,

        //TODO 항상최신화
        @Column(nullable = false)
        val expirationPeriod: Int,

        @Column(nullable = false)
        var quantity: Int,

        val priority: Int,


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "refrigerator_id")
        var refrigerator: Refrigerator,


        ) {

    fun updateIngredientQuantity(useQuantity: Int) {
        this.quantity -= useQuantity
    }

    fun addIngredientQuantity(quantity: Int) {
        this.quantity += quantity
    }

    companion object {
        fun of(ingredientCreateDto: IngredientCreateDto,refrigerator: Refrigerator): Ingredient {
            return Ingredient(
                    id = null,
                    name = ingredientCreateDto.name,
                    buyDate = ingredientCreateDto.buyDate,
                    expireDate = ingredientCreateDto.expireDate,
                    expirationPeriod = ingredientCreateDto.expirationPeriod,
                    quantity = ingredientCreateDto.quantity,
                    priority = 10,
                    refrigerator = refrigerator
            )
        }
    }
}