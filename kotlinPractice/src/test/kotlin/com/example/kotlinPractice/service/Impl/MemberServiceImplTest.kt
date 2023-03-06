package com.example.kotlinPractice.service.Impl

import com.example.kotlinPractice.domain.dto.kitchen.KitchenCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.domain.repository.KitchenRepository
import com.example.kotlinPractice.domain.repository.MemberRepository
import com.example.kotlinPractice.service.KitchenService
import com.example.kotlinPractice.service.MemberService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.web.WebAppConfiguration

@SpringBootTest
class MemberServiceImplTest @Autowired constructor(
         val memberService: MemberService,
         val memberRepository: MemberRepository,
         val kitchenService: KitchenService,
         val kitchenRepository: KitchenRepository,
) {

    @BeforeEach
    fun createMemberAndKitchen() {
        //given
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
        val createdKitchen = kitchenService.createKitchen(testKitchen)
        val kitchenId = kitchenRepository.findAll()[0].id!!
        //when
        val createdMember = memberService.createMember(testMember, kitchenId)

    }

    @AfterEach()
    fun delete() {
        memberRepository.deleteAll()
        kitchenRepository.deleteAll()
    }

    @Test
    fun createMember() {
        //given
        //when
        val member = memberRepository.findAll()[0]
        //then

        assertThat(member.experience).isEqualTo(0)
        assertThat(member.name).isEqualTo("testMember")
        assertThat(LevelType.intToType(member.level)).isEqualTo(LevelType.COMMI_CHEF)
    }

    @Test
    fun getMember() {

        //given
        val member = memberRepository.findAll()[0]
        //when
        val findById = memberService.getMember(member.id!!)
        //then
        assertThat(findById.experience).isEqualTo(0)
        assertThat(findById.name).isEqualTo("testMember")
        assertThat(findById.level).isEqualTo(LevelType.COMMI_CHEF)
    }

    @Test
    fun getMembers() {

        //given
        val secondTestMember = MemberCreateDto(
                name = "secondTestMember",
                level = LevelType.DEMI_CHEF,
                section = SectionType.HOT,
                experience = 3,
        )
        val kitchenId = kitchenRepository.findAll()[0].id!!
        memberService.createMember(secondTestMember, kitchenId)

        val sort = Sort.by("id").ascending()
        val pageable: Pageable = PageRequest.of(0, 10, sort)

        //when
        val members = memberService.getMembers(pageable)

        //then
        assertThat(members.totalElements).isEqualTo(2)
        assertThat(members.toList()[1].name).isEqualTo("secondTestMember")
        assertThat(members.toList()[1].level).isEqualTo(LevelType.DEMI_CHEF)
    }

    @Test
    fun updateMember() {
        //given
        val updateMemberDto = MemberUpdateDto(
                name = "updateMember",
                level = LevelType.HEAD_CHEF,
                section = SectionType.PASTRY,
                experience = 10,
        )

        val targetMember = memberRepository.findAll()[0]
        //when
        val updatedMember = memberService.updateMember(targetMember.id!!, updateMemberDto)
        //then
        assertThat(updatedMember.name).isEqualTo("updateMember")
        assertThat(updatedMember.level).isEqualTo(LevelType.HEAD_CHEF)
        assertThat(updatedMember.section).isEqualTo(SectionType.PASTRY)
        assertThat(updatedMember.experience).isEqualTo(10)

    }

    @Test
    fun removeMember() {
        //given
        val targetMember = memberRepository.findAll()[0]
        //when
        memberService.removeMember(targetMember.id!!)
        //then
        assertThat(memberRepository.findAll().size).isEqualTo(0)
    }
}