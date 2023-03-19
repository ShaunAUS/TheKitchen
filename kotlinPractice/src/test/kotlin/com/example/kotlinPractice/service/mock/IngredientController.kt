package com.example.kotlinPractice.service.mock

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientUseDto
import com.example.kotlinPractice.domain.dto.ingredient.UseIngredientDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.server.controller.IngredientController
import com.example.kotlinPractice.server.controller.PrepController
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.RefrigeratorService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate


@SpringBootTest
@AutoConfigureMockMvc // 서블릿 컨테이너 모킹,
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
class IngredientController @Autowired constructor(
        var mvc: MockMvc,
        val mapper: ObjectMapper,
        val kitchenService: KitchenService,
        val refrigeratorService: RefrigeratorService,
) {

    @BeforeEach
    fun set() {
        this.mvc = MockMvcBuilders.standaloneSetup(IngredientController::class).build()
    }

    @BeforeAll
    fun createKitchenAndRefrigerator() {
        kitchenService.createKitchen(KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        ))

        refrigeratorService.createRefrigerator(RefrigeratorCreateDto(
                name = "testRefrigerator"
        ), 1)

    }

    @Test
    fun buyIngredient() {
        mvc.perform(post("/v1/ingredient")
                .contentType("application/json")
                .content(mapper.writeValueAsString(AddIngredientDto(
                        kitchenId = 1,
                        refrigeratorId = 1,
                        ingredientCreateDtos = listOf(
                                IngredientCreateDto(
                                        name = "testIngredient",
                                        buyDate = LocalDate.of(2021, 1, 1),
                                        expireDate = LocalDate.of(2021, 1, 3),
                                        expirationPeriod = 2,
                                        quantity = 5
                                ),
                                IngredientCreateDto(
                                        name = "secondTestIngredient",
                                        buyDate = LocalDate.of(2021, 1, 1),
                                        expireDate = LocalDate.of(2021, 1, 5),
                                        expirationPeriod = 4,
                                        quantity = 5
                                )
                        )
                ))))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("testRefrigerator"))
                .andExpect(jsonPath("$.ingredients[0].name").value("testIngredient"))
                .andExpect(jsonPath("$.ingredients[0].quantity").value(5))
                .andExpect(jsonPath("$.ingredients[0].expirationPeriod").value(2))
                .andExpect(jsonPath("$.ingredients[1].name").value("secondTestIngredient"))
                .andExpect(jsonPath("$.ingredients[1].quantity").value(5))
                .andExpect(jsonPath("$.ingredients[1].expirationPeriod").value(4))
    }

    @Test
    fun useIngredient() {
        mvc.perform(patch("/v1/ingredient")
                .contentType("application/json")
                .content(mapper.writeValueAsString(UseIngredientDto(
                        kitchenId = 1,
                        refrigeratorId = 1,
                        ingredientUseDtos = listOf(
                                IngredientUseDto(
                                        name = "testIngredient",
                                        quantity = 1
                                ),
                                IngredientUseDto(
                                        name = "secondTestIngredient",
                                        quantity = 1
                                ),
                        )
                ))))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("testRefrigerator"))
                .andExpect(jsonPath("$.ingredients[0].name").value("testIngredient"))
                .andExpect(jsonPath("$.ingredients[0].quantity").value(4))
                .andExpect(jsonPath("$.ingredients[1].name").value("secondTestIngredient"))
                .andExpect(jsonPath("$.ingredients[1].quantity").value(4))


    }


}