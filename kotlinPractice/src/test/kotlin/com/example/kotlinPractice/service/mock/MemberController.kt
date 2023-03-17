package com.example.kotlinPractice.service.mock

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.kitchen.KitchenInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.server.controller.MemberController
import com.example.kotlinPractice.service.KitchenService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
class MemberController @Autowired constructor(
        var mvc: MockMvc,
        val mapper: ObjectMapper,
        val kitchenService: KitchenService,

        ) {
    @BeforeEach
    fun set() {
        this.mvc = MockMvcBuilders.standaloneSetup(MemberController::class).build()

    }

    @BeforeAll
    fun createKitchen() {
        kitchenService.createKitchen(KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        ))
    }


    @Test
    @Order(1)
    fun createMember() {
        mvc.perform(MockMvcRequestBuilders.post("/v1/member")
                .param("kitchenId", 1.toString())
                .content(mapper.writeValueAsString(MemberCreateDto(
                        name = "testMember",
                        level = LevelType.COMMI_CHEF,
                        section = SectionType.COLD,
                        experience = 0,

                        )))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                /*.andExpect(content().json(mapper.writeValueAsString(MemberInfoDto(
                        name = "testMember",
                        level = LevelType.COMMI_CHEF,
                        section = SectionType.COLD,
                        experience = 0,
                        kitchen = KitchenInfoDto(
                                name = "testKitchen",
                                location = "testLocation"
                        )
                ))))*/

                //TODO 위처럼 JSON 리턴값으로 체크해야되나 아니면 아래처럼  체크해야되나
                .andExpect(jsonPath("$.name").value("testMember"))
                .andExpect(jsonPath("$.level").value(LevelType.COMMI_CHEF))
                .andExpect(jsonPath("$.section").value(SectionType.COLD))
                .andExpect(jsonPath("$.experience").value(0))
                .andExpect(jsonPath("$.kitchen.name").value("testKitchen"))
                .andExpect(jsonPath("$.kitchen.location").value("testLocation"))
    }

    @Test
    @Order(3)
    fun getMembers() {
        mvc.perform(MockMvcRequestBuilders.post("/v1/member/all")
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

    @Test
    @Order(2)
    fun getMember() {
        mvc.perform(MockMvcRequestBuilders.post("/v1/member/{targetMemberId}", 1)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                /*.andExpect(content().json(mapper.writeValueAsString(MemberInfoDto(
                        name = "testMember",
                        level = LevelType.COMMI_CHEF,
                        section = SectionType.COLD,
                        experience = 0,
                        kitchen = KitchenInfoDto(
                                name = "testKitchen",
                                location = "testLocation"
                        )
                ))))*/
                .andExpect(jsonPath("$.name").value("testMember"))
                .andExpect(jsonPath("$.level").value(LevelType.COMMI_CHEF))
                .andExpect(jsonPath("$.section").value(SectionType.COLD))
                .andExpect(jsonPath("$.experience").value(0))
                .andExpect(jsonPath("$.kitchen.name").value("testKitchen"))
                .andExpect(jsonPath("$.kitchen.location").value("testLocation"))

    }

    @Test
    @Order(4)
    fun updateMember() {
        mvc.perform(MockMvcRequestBuilders.get("/v1/member/{targetMemberId}", 1)
                .content(mapper.writeValueAsString(MemberUpdateDto(
                        name = "updateMember",
                        level = LevelType.HEAD_CHEF,
                        SectionType.PASTRY,
                        experience = 1
                )))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                /*.andExpect(content().json(mapper.writeValueAsString(MemberInfoDto(
                        name = "updateMember",
                        level = LevelType.HEAD_CHEF,
                        section = SectionType.PASTRY,
                        experience = 1,
                        kitchen = KitchenInfoDto(
                                name = "testKitchen",
                                location = "testLocation"
                        )
                ))))*/
                .andExpect(jsonPath("$.name").value("updateMember"))
                .andExpect(jsonPath("$.level").value(LevelType.HEAD_CHEF))
                .andExpect(jsonPath("$.section").value(SectionType.PASTRY))
                .andExpect(jsonPath("$.experience").value(1))
                .andExpect(jsonPath("$.kitchen.name").value("testKitchen"))
                .andExpect(jsonPath("$.kitchen.location").value("testLocation"))

    }

    @Test
    @Order(5)
    fun deleteMember() {
        mvc.perform(MockMvcRequestBuilders.patch("/v1/member/{targetMemberId}", 1)
                .contentType(MediaType.APPLICATION_JSON))  //TODO pathvariable 만 있을떄는??
                .andExpect(status().isOk)
    }


}