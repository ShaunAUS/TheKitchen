package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.repository.IngredientRepository
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.RefrigeratorRepository
import com.example.kotlinPractice.service.IngredientService
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.RefrigeratorService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
class RefrigeratorServiceImplTest @Autowired constructor(
        val refrigeratorService: RefrigeratorService,
        val kitchenService: KitchenService,
        val ingredientService: IngredientService,
        val kitchenRepository: KitchenRepository,
        val refrigeratorRepository: RefrigeratorRepository,
        val ingredientRepository: IngredientRepository
){
    @BeforeAll
    fun before(){

        val testKitchen = KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        )
        kitchenService.createKitchen(testKitchen)


        val testKitchenId = kitchenRepository.findAll()[0].id!!
        val createRefrigeratorDto = RefrigeratorCreateDto(
                name = "testRefrigerator",
        )
        refrigeratorService.createRefrigerator(createRefrigeratorDto, testKitchenId)

        ////분리 need
        val testRefrigerator = refrigeratorRepository.findAll()[0]
        val ingredientCreateDtos = mutableListOf<IngredientCreateDto>()
        ingredientCreateDtos.addAll(listOf(
                IngredientCreateDto(
                        name = "양파",
                        quantity = 10,
                        buyDate = LocalDate.of(2021, 8, 1),
                        expireDate = LocalDate.of(2021, 8, 3),
                        expirationPeriod = 5,
                ),
                IngredientCreateDto(
                        name = "당근",
                        quantity = 10,
                        buyDate = LocalDate.of(2021, 8, 1),
                        expireDate = LocalDate.of(2021, 8, 3),
                        expirationPeriod = 5,
                )
        )
        )
        val addIngredientDto = AddIngredientDto(testKitchenId, testRefrigerator.id!!, ingredientCreateDtos)
        ingredientService.addIngredient(addIngredientDto)

    }

    @AfterAll
    fun after(){
        ingredientRepository.deleteAll()
        refrigeratorRepository.deleteAll()
        kitchenRepository.deleteAll()

    }

    @Test
    fun createRefrigerator() {
        //given
        //when
        val savedRefrigerator = refrigeratorRepository.findAll()[0]
        //then
        assertThat(savedRefrigerator.name).isEqualTo("testRefrigerator")

    }

    @Test
    @Transactional
    fun updateRefrigerator() {
        //given
        val testRefrigerator = refrigeratorRepository.findAll()[0]

        //when
        refrigeratorService.updateRefrigerator(testRefrigerator.id!!)

        //then
        assertThat(testRefrigerator.ingredients[0].expirationPeriod).isEqualTo(2)
        assertThat(testRefrigerator.ingredients[1].expirationPeriod).isEqualTo(2)
    }

    @Test
    @Transactional
    fun getRefrigerator() {
        //given
        val testRefrigerator = refrigeratorRepository.findAll()[0]

        //when
        val refrigerator = refrigeratorService.getRefrigerator(testRefrigerator.id!!)

        //then
        assertThat(refrigerator.name).isEqualTo("testRefrigerator")
        assertThat(refrigerator.ingredients[0].name).isEqualTo("양파")
        assertThat(refrigerator.ingredients[1].name).isEqualTo("당근")
        assertThat(refrigerator.ingredients[0].expireDate).isEqualTo(LocalDate.of(2021, 8, 3))
        assertThat(refrigerator.ingredients[1].expireDate).isEqualTo(LocalDate.of(2021, 8, 3))
    }

}