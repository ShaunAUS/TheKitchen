package com.example.kotlinPractice.service.mock

import com.example.kotlinPractice.domain.dto.ingredient.AddIngredientDto
import com.example.kotlinPractice.domain.dto.ingredient.IngredientCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.server.controller.MemberController
import com.example.kotlinPractice.service.IngredientService
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.RefrigeratorService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
class RefrigeratorController @Autowired constructor(
        var mvc: MockMvc,
        val mapper: ObjectMapper,
        val kitchenService: KitchenService,
        val ingredientService: IngredientService,
        val refrigeratorService: RefrigeratorService,
) {

    @BeforeEach
    fun set() {
        this.mvc = MockMvcBuilders.standaloneSetup(RefrigeratorController::class).build()

    }

    @BeforeAll
    fun before() {

        kitchenService.createKitchen(KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        ))

        refrigeratorService.createRefrigerator(RefrigeratorCreateDto(
                name = "testRefrigerator"
        ), 1)

        ingredientService.addIngredient(AddIngredientDto(
                kitchenId = 1,
                refrigeratorId = 1,
                ingredientCreateDtos = listOf(
                        IngredientCreateDto(
                                name = "carrot",
                                buyDate = LocalDate.of(2020, 1, 1),
                                expireDate = LocalDate.of(2020, 1, 3),
                                expirationPeriod = 1,
                                quantity = 20,
                        ),
                        IngredientCreateDto(
                                name = "potato",
                                buyDate = LocalDate.of(2020, 1, 1),
                                expireDate = LocalDate.of(2020, 1, 3),
                                expirationPeriod = 1,
                                quantity = 20,
                        )
                )
        ))
    }

    @Test
    @Order(1)
    fun createRefrigeratorTest() {
        mvc.perform(post("/v1/refrigerator")
                .param("kitchenId", 1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(RefrigeratorCreateDto(
                        name = "secondTestRefrigerator"
                ))))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("secondTestRefrigerator"))
    }


    @Test
    @Order(3)
    fun updateRefrigeratorTest() {
        mvc.perform(patch("/v1/refrigerator/uptodate")
                .param("refrigeratorId", 1.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("testRefrigerator"))
                .andExpect(jsonPath("$.ingredients[0].expirationPeriod").value("2"))
                .andExpect(jsonPath("$.ingredients[1].expirationPeriod").value("2"))
    }

    @Test
    @Order(2)
    fun getRefrigeratorTest() {

        mvc.perform(get("/v1/refrigerator/{refrigeratorId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("testRefrigerator"))
                .andExpect(jsonPath("$.ingredients[0].name").value("carrot"))
                .andExpect(jsonPath("$.ingredients[0].quantity").value("20"))
                .andExpect(jsonPath("$.ingredients[1].name").value("potato"))
                .andExpect(jsonPath("$.ingredients[1].quantity").value("20"))
    }

}


