package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.UseIngredientDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.domain.entity.Ingredient
import com.example.kotlinPractice.domain.entity.Kitchen
import com.example.kotlinPractice.domain.entity.Refrigerator
import com.example.kotlinPractice.domain.repository.IngredientRepository
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.RefrigeratorRepository
import com.example.kotlinPractice.service.IngredientService
import com.group.libraryapp.utils.empty
import com.group.libraryapp.utils.findByIdOrThrow
import com.group.libraryapp.utils.notEnough
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class IngredientServiceImpl(

        private val refrigeratorRepository: RefrigeratorRepository,
        private val ingredientRepository: IngredientRepository,
        private val kitchenRepository: KitchenRepository,
) : IngredientService {

    //냉장고는 여러개일 수 있다.
    @Transactional
    override fun useIngredient(useIngredientDto: UseIngredientDto): RefrigeratorInfoDto {

        val refrigeratorId = useIngredientDto.refrigeratorId
        val refrigerator = findRefrigeratorOrThrow(refrigeratorId)


        for (useIngredient in useIngredientDto.ingredientUseDtoList) {
            val ingredient = ingredientRepository.findByNameAndRefrigeratorId(useIngredient.name, refrigeratorId) ?: empty()

            ingredient.updateIngredientQuantity(useIngredient.quantity)

            noticeIfNotEnoughtIngredient(ingredient.quantity)
        }

        return RefrigeratorInfoDto.of(refrigerator)

    }

    @Transactional
    override fun addIngredient(addIngredientDto: AddIngredientDto): RefrigeratorInfoDto {
        //주방 ,냉장고 있나 확인
        findKitchenOrThrow(addIngredientDto.kitchenId)
        val refrigerator = findRefrigeratorOrThrow(addIngredientDto.refrigeratorId)

        addIngredientDto.ingredientCreateDtoList.stream()
                .map { ingredientDto ->
                    ingredientRepository.save(Ingredient.of(ingredientDto).setUpRefrigerator(refrigerator)) }

        return RefrigeratorInfoDto.of(refrigerator)

    }

    private fun findKitchenOrThrow(kitchenId: Long) : Kitchen {
        return kitchenRepository.findByIdOrThrow(kitchenId)
    }

    private fun findRefrigeratorOrThrow(refrigeratorId: Long): Refrigerator {
        return refrigeratorRepository.findByIdOrThrow(refrigeratorId)
    }
    private fun noticeIfNotEnoughtIngredient(quantity: Int) {
        if(quantity > 10){
            notEnough()
        }
    }

}