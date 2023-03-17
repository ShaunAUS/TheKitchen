package com.example.kotlinPractice.service.mock

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.server.controller.KitchenController
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@SpringBootTest
@AutoConfigureMockMvc // 서블릿 컨테이너 모킹,
class KitchenController @Autowired constructor(
        var mvc: MockMvc,
        val mapper : ObjectMapper
){

    @BeforeEach
    fun setMockMvc(){
        this.mvc = MockMvcBuilders.standaloneSetup(KitchenController::class).build()
    }

    @Test
    fun createKitchenTest(){

         mvc.perform(post("/v1/kitchen")
                .content(mapper.writeValueAsString(KitchenCreateDto(
                        name = "testKitchen",
                        location = "testLocation"
                )))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                 .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(KitchenInfoDto(
                        name = "testKitchen",
                        location = "testLocation"
                ))))
    }
}