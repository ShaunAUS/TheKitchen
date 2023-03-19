package com.example.kotlinPractice.service.mock

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.enums.ExecutionType
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.server.controller.MemberController
import com.example.kotlinPractice.server.controller.PrepController
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.MemberService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc // 서블릿 컨테이너 모킹,
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @AfterAll , @BeforeAll
class PrepController @Autowired constructor(
        var mvc: MockMvc,
        val mapper: ObjectMapper,
        val memberService: MemberService,
        val kitchenService: KitchenService
) {

    @BeforeAll
    fun createMember() {

        kitchenService.createKitchen(KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        ))

        memberService.createMember(MemberCreateDto(
                name = "testMember",
                level = LevelType.COMMI_CHEF,
                section = SectionType.PASTRY,
                experience = 2,
                ), 1)
    }

    @BeforeEach
    fun set() {
        this.mvc = MockMvcBuilders.standaloneSetup(PrepController::class).build()
    }

    @Test
    @Order(1)
    fun createPrep() {

        val prepCreateDtos = listOf(
                PrepCreateDto(
                        job = "testJob",
                        priority = 1,
                        executionType = ExecutionType.BEFORE,
                        deadLine = LocalDate.now()
                ),
                PrepCreateDto(
                        job = "secondTestJob",
                        priority = 2,
                        executionType = ExecutionType.BEFORE,
                        deadLine = LocalDate.now()
                )
        )
        mvc.perform(post("/v1/prep")
                .param("targetMemberId", "1")
                .content(mapper.writeValueAsString(prepCreateDtos))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("testMember"))
                .andExpect(jsonPath("$.level").value(LevelType.COMMI_CHEF))
                .andExpect(jsonPath("$.section").value(SectionType.PASTRY))
                .andExpect(jsonPath("$.experience").value(2))
                .andExpect(jsonPath("$.prepList[0].job").value("testJob"))
                .andExpect(jsonPath("$.prepList[0].priority").value(1))
                .andExpect(jsonPath("$.prepList[0].executionType").value(ExecutionType.BEFORE))
                .andExpect(jsonPath("$.prepList[0].deadLine").value(LocalDate.now()))
                .andExpect(jsonPath("$.prepList[1].job").value("secondTestJob"))
                .andExpect(jsonPath("$.prepList[1].priority").value(2))
                .andExpect(jsonPath("$.prepList[1].executionType").value(ExecutionType.BEFORE))
                .andExpect(jsonPath("$.prepList[1].deadLine").value(LocalDate.now()))


    }

    @Test
    @Order(3)
    fun finishPrep() {
        mvc.perform(patch("/v1/prep/{targetMemberId}",1))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.executionStatus").value(ExecutionType.DONE))
    }

    @Test
    @Order(2)
    fun checkMyPrep() {

        mvc.perform(get("/v1/prep/my")
                .param("targetMemberId", "1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].job").value("testJob"))
                .andExpect(jsonPath("$[0].priority").value(1))
                .andExpect(jsonPath("$[0].executionType").value(ExecutionType.BEFORE))
                .andExpect(jsonPath("$[0].deadLine").value(LocalDate.now()))
                .andExpect(jsonPath("$[1].job").value("secondTestJob"))
                .andExpect(jsonPath("$[1].priority").value(2))
                .andExpect(jsonPath("$[1].executionType").value(ExecutionType.BEFORE))
                .andExpect(jsonPath("$[1].deadLine").value(LocalDate.now())

        )

    }


}