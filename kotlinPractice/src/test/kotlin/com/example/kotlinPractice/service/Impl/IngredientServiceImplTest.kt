package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.ingredient.UseIngredientDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.repository.IngredientRepository
import org.assertj.core.api.Assertions.assertThat
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.RefrigeratorRepository
import com.example.kotlinPractice.service.IngredientService
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.RefrigeratorService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
class IngredientServiceImplTest @Autowired constructor(

        val ingredientService: IngredientService,
        val kitchenService: KitchenService,
        val refrigeratorService: RefrigeratorService,
        val kitchenRepository: KitchenRepository,
        val refrigeratorRepository: RefrigeratorRepository,
        val ingredientRepository: IngredientRepository,
) {

    @BeforeEach
    fun before() {

        val testKitchen = KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        )
        kitchenService.createKitchen(testKitchen)
        val testKitchenId = kitchenRepository.findAll()[0].id!!


        val testRefrigerator = RefrigeratorCreateDto(
                name = "testRefrigerator",
        )
        refrigeratorService.createRefrigerator(testRefrigerator, testKitchenId)
    }

    @AfterAll
    fun after() {
        ingredientRepository.deleteAll()
        refrigeratorRepository.deleteAll()
        kitchenRepository.deleteAll()
    }

    @Test
    @Transactional
    @Order(1)
    @Rollback(false)
    fun addIngredient() {
        //given
        val testKitchenId = kitchenRepository.findAll()[0].id!!
        val testRefrigeratorId = refrigeratorRepository.findAll()[0].id!!

        val ingredientCreateDtos = mutableListOf<IngredientCreateDto>()
        ingredientCreateDtos.addAll(listOf(
                IngredientCreateDto(
                        name = "양파",
                        quantity = 10,
                        buyDate = LocalDate.of(2021, 8, 1),
                        expireDate = LocalDate.of(2021, 8, 3),
                        expirationPeriod = 2,
                ),
                IngredientCreateDto(
                        name = "당근",
                        quantity = 10,
                        buyDate = LocalDate.of(2021, 8, 1),
                        expireDate = LocalDate.of(2021, 8, 3),
                        expirationPeriod = 2,
                )
        )
        )
        val addIngredientDto = AddIngredientDto(testKitchenId, testRefrigeratorId, ingredientCreateDtos)

        //when
        ingredientService.addIngredient(addIngredientDto)

        val onion = ingredientRepository.findByName("양파")!!
        val carrot = ingredientRepository.findByName("당근")!!

        //then
        assertThat(onion.quantity).isEqualTo(10)
        assertThat(carrot.quantity).isEqualTo(10)
        assertThat(onion.buyDate).isEqualTo(LocalDate.of(2021, 8, 1))
        assertThat(carrot.buyDate).isEqualTo(LocalDate.of(2021, 8, 1))

    }

    @Test
    @Transactional
    @Order(2)
    fun useIngredient() {

        //given
        val testKitchenId = kitchenRepository.findAll()[0].id!!
        val testRefrigeratorId = refrigeratorRepository.findAll()[0].id!!

        val ingredientUseDtos = mutableListOf<IngredientUseDto>()
        ingredientUseDtos.addAll(
                listOf(
                        IngredientUseDto(
                                name = "양파",
                                quantity = 2
                        ),
                        IngredientUseDto(
                                name = "당근",
                                quantity = 5
                        )
                )
        )

        val useIngredientDto = UseIngredientDto(
                kitchenId = testKitchenId,
                refrigeratorId = testRefrigeratorId,
                ingredientUseDtos = ingredientUseDtos
        )

        //when
        ingredientService.useIngredient(useIngredientDto)
        val onion = ingredientRepository.findByName("양파")!!
        val carrot = ingredientRepository.findByName("당근")!!

        //then
        assertThat(onion.quantity).isEqualTo(8)
        assertThat(carrot.quantity).isEqualTo(5)

    }


}