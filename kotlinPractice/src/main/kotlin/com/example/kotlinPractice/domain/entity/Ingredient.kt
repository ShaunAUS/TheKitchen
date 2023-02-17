package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.utils.ModelMapper
import com.group.libraryapp.utils.empty
import jakarta.persistence.*
import org.modelmapper.Converter
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
        val buyDate: LocalDateTime,

        @Column(nullable = false)
        val expireDate: LocalDateTime,

        //TODO 항상최신화
        @Column(nullable = false)
        val expirationPeriod: LocalDateTime,

        @Column(nullable = false)
        var quantity: Int,

        val priority: Int?,

        @ManyToOne
        @JoinColumn(name = "menu_id")
        val menu: Menu,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "refrigerator_id")
        var refrigerator: Refrigerator,


        ) {

    @PrePersist
    fun prePersist9() {
        this.priority ?: 0
    }

    fun updateIngredientQuantity(useQuantity: Int) {
        this.quantity -= useQuantity
    }

    fun setUpRefrigerator(refrigerator: Refrigerator): Ingredient {
        this.refrigerator = refrigerator
        return this
    }

    companion object {
        fun of(ingredientCreateDto: IngredientCreateDto?): Ingredient {
            return ModelMapper.getMapper()
                    .map(ingredientCreateDto, Ingredient::class.java)
        }

        fun INGREDIENT_LIST_CONVERTER(): Converter<List<Ingredient>, List<IngredientInfoDto>> =
                Converter { context
                    -> context.source?.stream()
                        ?.map { ingredient -> IngredientInfoDto.of(ingredient) }
                        ?.collect(Collectors.toList()) ?: empty()
                }
    }
}