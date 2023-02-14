package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto.Companion.INGREDIENTDTO_LIST_CONVERTER
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.utils.ModelMapper
import jakarta.persistence.*

//냉장고는 여러개가 될수 있다.
@Entity
class Refrigerator(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        val name: String,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen,

        @OneToMany(mappedBy = "refrigerator", cascade = [CascadeType.ALL], orphanRemoval = true)
        val ingredients: List<Ingredient>,

        ) {

    companion object {
        fun of(refrigeratorCreateDto: RefrigeratorCreateDto): Refrigerator {
             return ModelMapper.getMapper()
                    .typeMap(RefrigeratorCreateDto::class.java, Refrigerator::class.java)
                    .addMappings { mapper ->
                        mapper.using(INGREDIENTDTO_LIST_CONVERTER())
                                .map(RefrigeratorCreateDto::ingredients, Refrigerator::ingredients)
                    }
                     .map(refrigeratorCreateDto)
        }
    }


}
