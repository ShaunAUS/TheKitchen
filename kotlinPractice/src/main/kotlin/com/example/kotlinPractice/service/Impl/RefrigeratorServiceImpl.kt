package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.domain.entity.Kitchen
import com.example.kotlinPractice.domain.entity.Refrigerator
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.RefrigeratorRepository
import com.example.kotlinPractice.service.RefrigeratorService
import com.group.libraryapp.utils.findByIdOrThrow
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Slf4j
class RefrigeratorServiceImpl(
        private val refrigeratorRepository: RefrigeratorRepository,
        private val kitchenRepository: KitchenRepository,
) : RefrigeratorService {

    override fun getRefrigerator(refrigeratorId: Long): RefrigeratorInfoDto {
        return RefrigeratorInfoDto.of(findRefrigeratorOrThrow(refrigeratorId))
    }

    override fun remove(refrigeratorId: Long) {
        refrigeratorRepository.deleteById(refrigeratorId)
    }

    override fun createRefrigerator(refrigeratorCreateDto: RefrigeratorCreateDto,kitchenId:Long): RefrigeratorInfoDto {
        val kitchen = findKitchenOrThrow(kitchenId)
        val refrigerator = Refrigerator.of(refrigeratorCreateDto,kitchen)
        return RefrigeratorInfoDto.of(refrigeratorRepository.save(refrigerator))
    }

    private fun findKitchenOrThrow(kitchenId: Long): Kitchen {
        return kitchenRepository.findByIdOrThrow(kitchenId)
    }

    private fun findRefrigeratorOrThrow(refrigeratorId: Long): Refrigerator {
        val refrigerator = refrigeratorRepository.findByIdOrThrow(refrigeratorId)
        upToDateIngredientDate(refrigerator)
        return refrigerator
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