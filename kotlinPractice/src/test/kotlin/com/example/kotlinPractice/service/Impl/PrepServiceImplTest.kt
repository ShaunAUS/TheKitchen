package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.enums.ExecutionType
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.MemberRepository
import com.example.kotlinPractice.domain.repository.PrepRepository
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.MemberService
import com.example.kotlinPractice.service.PrepService
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@SpringBootTest
class PrepServiceImplTest @Autowired constructor(

        val prepService: PrepService,
        val memberService: MemberService,
        val kitchenService: KitchenService,
        val kitchenRepository: KitchenRepository,
        val memberRepository: MemberRepository,
        val prepRepository: PrepRepository,
) {

    //TODO need to seperate
    @BeforeEach
    fun Before() {

        val testMember = MemberCreateDto(
                name = "testMember",
                level = LevelType.COMMI_CHEF,
                section = SectionType.COLD,
                experience = 0,
        )

        val testKitchen = KitchenCreateDto(
                name = "testKitchen",
                location = "testLocation"
        )
        kitchenService.createKitchen(testKitchen)
        val kitchenId = kitchenRepository.findAll()[0].id!!
        memberService.createMember(testMember, kitchenId)

        val targetMemberId = memberRepository.findAll()[0].id!!

        val prepCreateDtos = mutableListOf<PrepCreateDto>()
        prepCreateDtos.addAll(
                listOf(
                        PrepCreateDto(
                                job = "양파 썰기",
                                priority = 10,
                                executionType = ExecutionType.BEFORE,
                                deadLine = LocalDate.of(2021, 8, 1),
                        ),
                        PrepCreateDto(
                                job = "당근 썰기",
                                priority = 10,
                                executionType = ExecutionType.BEFORE,
                                deadLine = LocalDate.of(2021, 8, 2),
                        )
                ))
        prepService.createPrepToTargetMember(targetMemberId, prepCreateDtos)

    }

    @AfterEach
    fun delete() {
        prepRepository.deleteAll()
        memberRepository.deleteAll()
        kitchenRepository.deleteAll()
    }

    @Test
    @Transactional // TODO  메인코드에서는 안필요한데 테스트 코드에서 필요한 이유는???
    fun createPrepToTargetMember() {

        //given
        //when
        val targetMemberId = memberRepository.findAll()[0].id!!
        //then
        assertThat(prepService.getMyPrep(targetMemberId).size).isEqualTo(2)
        assertThat(prepService.getMyPrep(targetMemberId)[0].job).isEqualTo("양파 썰기")
        assertThat(prepService.getMyPrep(targetMemberId)[0].priority).isEqualTo(10)
        assertThat(prepService.getMyPrep(targetMemberId)[1].job).isEqualTo("당근 썰기")

    }

    @Test
    @Transactional
    fun updatePrepStatus() {

        //given
        val firstPrep = prepRepository.findAll()[0].id!!
        val secondPrep = prepRepository.findAll()[1].id!!

        //when
        val updatedFirstPrep = prepService.updatePrepStatus(firstPrep)
        val updatedSecondPrep = prepService.updatePrepStatus(secondPrep)

        //then
        assertThat(updatedFirstPrep.executionType).isEqualTo(ExecutionType.DONE)
        assertThat(updatedSecondPrep.executionType).isEqualTo(ExecutionType.DONE)

    }

    @Test
    @Transactional // TODO  메인코드에서는 안필요한데 테스트 코드에서 필요한 이유는???
    fun getMyPrep() {

        //given
        val targetMemberId = memberRepository.findAll()[0].id!!
        //when
        val myPreps = prepService.getMyPrep(targetMemberId)
        //then
        assertThat(myPreps.size).isEqualTo(2)
        assertThat(myPreps[0].job).isEqualTo("양파 썰기")
        assertThat(myPreps[1].priority).isEqualTo(10)
        assertThat(myPreps[1].job).isEqualTo("당근 썰기")

    }
}