package com.example.kotlinPractice.domain.dto.ingredient

import com.example.kotlinPractice.domain.entity.Ingredient
import com.group.libraryapp.utils.empty
import org.modelmapper.Converter
import java.time.LocalDate
import java.util.stream.Collectors

data class IngredientCreateDto(

        val name: String,
        val buyDate: LocalDate,
        val expireDate: LocalDate,
        val expirationPeriod: Int,
        val quantity: Int,
){
    companion object{

    }
}
