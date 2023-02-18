package com.example.kotlinPractice.domain.dto.refrigerator

import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto.Companion.INGREDIENTDTO_LIST_CONVERTER
import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.entity.Ingredient.Companion.INGREDIENT_LIST_CONVERTER
import com.example.kotlinPractice.domain.entity.Refrigerator
import com.example.kotlinPractice.utils.ModelMapper
import org.modelmapper.Converter

data class RefrigeratorInfoDto(

        val id: Long?,
        val name: String,
        val ingredients: List<IngredientInfoDto>

        ) {
        //TODO   List<Ingredient> -> List<IngredinetInfoDto
        companion object {
                fun of(refrigerator: Refrigerator?): RefrigeratorInfoDto {
                         return ModelMapper.getMapper()
                                .typeMap(Refrigerator::class.java, RefrigeratorInfoDto::class.java)
                                /*.addMappings { mapper ->
                                        mapper.using(INGREDIENT_LIST_CONVERTER())
                                                .map(Refrigerator::ingredients, RefrigeratorInfoDto::ingredients)
                                }*/
                                .map(refrigerator)

                }
        }
}