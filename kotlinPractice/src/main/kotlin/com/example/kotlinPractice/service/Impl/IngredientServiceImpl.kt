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
import java.time.Duration

@Service
@Slf4j
class IngredientServiceImpl(

        private val refrigeratorRepository: RefrigeratorRepository,
        private val ingredientRepository: IngredientRepository,
        private val kitchenRepository: KitchenRepository,

        ) : IngredientService {

    @Transactional
    override fun useIngredient(useIngredientDto: UseIngredientDto): RefrigeratorInfoDto {

        val refrigeratorId = useIngredientDto.refrigeratorId
        val refrigerator = findRefrigeratorOrThrow(refrigeratorId)

        for (useIngredient in useIngredientDto.ingredientUseDtos) {
            val ingredient = ingredientRepository.findByNameAndRefrigeratorId(useIngredient.name, refrigeratorId)
                    ?: empty()
            ingredient.updateIngredientQuantity(useIngredient.quantity)

            noticeIfNotEnoughtQuantity(ingredient.quantity)
        }

        upToDateIngredientDate(refrigerator)


        return RefrigeratorInfoDto.of(refrigerator)
    }

    private fun noticeIfNotEnoughtQuantity(quantity: Int) {
        if (quantity < 2) {
            notEnough()
        }
    }

    @Transactional
    override fun addIngredient(addIngredientDto: AddIngredientDto): RefrigeratorInfoDto {
        //주방 ,냉장고 있나 확인
        findKitchenOrThrow(addIngredientDto.kitchenId)
        val refrigerator = findRefrigeratorOrThrow(addIngredientDto.refrigeratorId)

        //TODO 중간에 들어온 재료 날짜다르게 로직 필요 , 지금은 그냥 더하기
        addIngredientDto.ingredientCreateDtos
                .forEach { ingredientDto ->
                    ingredientRepository.findByName(ingredientDto.name)?.addIngredientQuantity(ingredientDto.quantity)
                            ?: ingredientRepository.save(Ingredient.of(ingredientDto, refrigerator))
                }


        return RefrigeratorInfoDto.of(refrigerator)

    }

    private fun findKitchenOrThrow(kitchenId: Long): Kitchen {
        return kitchenRepository.findByIdOrThrow(kitchenId)
    }

    private fun findRefrigeratorOrThrow(refrigeratorId: Long): Refrigerator {
        return refrigeratorRepository.findByIdOrThrow(refrigeratorId)
    }

    @Transactional
    private fun upToDateIngredientDate(refrigerator: Refrigerator) {
        refrigerator.ingredients
                .stream()
                .forEach { ingredient ->
                    ingredient.updateExpirationPeriod(Duration.between(ingredient.buyDate.atStartOfDay(), ingredient.expireDate.atStartOfDay()).toDays())
                }
    }


}