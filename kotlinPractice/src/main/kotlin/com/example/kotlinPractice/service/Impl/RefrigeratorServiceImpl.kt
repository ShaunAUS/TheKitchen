package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.ingredient.IngredientInfoDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.domain.entity.Refrigerator
import com.example.kotlinPractice.domain.repository.IngredientRepository
import com.example.kotlinPractice.domain.repository.RefrigeratorRepository
import com.example.kotlinPractice.service.RefrigeratorService
import com.group.libraryapp.utils.empty
import com.group.libraryapp.utils.fail
import com.group.libraryapp.utils.findByIdOrThrow
import com.group.libraryapp.utils.notEnough
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class RefrigeratorServiceImpl(

        private val refrigeratorRepository: RefrigeratorRepository,
        private val ingredientRepository: IngredientRepository,
) : RefrigeratorService {
    @Transactional
    override fun useIngredient(refrigeratorId: Long, useIngredients: List<IngredientUseDto>): RefrigeratorInfoDto {

        val refrigerator = findRefrigeratorOrThrow(refrigeratorId)

        for (useIngredient in useIngredients) {
            val ingredient = ingredientRepository.findByNameAndRefrigeratorId(useIngredient.name, refrigeratorId) ?: empty()
            ingredient.updateIngredientQuantity(useIngredient.quantity)

            noticeNotEnoughtIngredient(ingredient.quantity)
        }

        return RefrigeratorInfoDto.of(refrigerator)

    }

    override fun addIngredient(refrigeratorId: Long, refrigeratorCreateDto: RefrigeratorCreateDto): RefrigeratorInfoDto {
        findRefrigeratorOrThrow(refrigeratorId)

        val savedRefrigeratorWithIngredients = refrigeratorRepository.save(Refrigerator.of(refrigeratorCreateDto))

        return RefrigeratorInfoDto.of(savedRefrigeratorWithIngredients)

    }

    @Transactional(readOnly = true)
    override fun getRefrigerator(refrigeratorId: Long): RefrigeratorInfoDto {
        return RefrigeratorInfoDto.of(findRefrigeratorOrThrow(refrigeratorId))
    }

    private fun findRefrigeratorOrThrow(refrigeratorId: Long): Refrigerator? {
        return refrigeratorRepository.findByIdOrThrow(refrigeratorId)
    }

    private fun noticeNotEnoughtIngredient(quantity: Int) {
        if(quantity > 10){
            notEnough()
        }
    }

}