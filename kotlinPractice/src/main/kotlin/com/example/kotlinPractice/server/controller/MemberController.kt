package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class MemberController(

        private val memberService: MemberService,
) {


    @GetMapping("")
    fun registerMember(
            @RequestBody memberCreateDto: MemberCreateDto,
    ): MemberInfoDto {

        return memberService.register(memberCreateDto);
    }


    @PostMapping("/all")
    fun getMembers(
            @PageableDefault(sort = {"member_id"}, direction = Sort.Direction.DESC, size = 10) pageable: Pageable,
    ): Page<MemberInfoDto> {
        return memberService.getMembers(pageable);

    }

    @GetMapping("/{memberNo}")
    fun getMember(
            @PathVariable memberNo: Long,
    ): MemberInfoDto {
        return memberService.getMember(memberNo)
    }

    @PatchMapping("/{memberNo}")
    fun updateMember(
            @PathVariable memberNo: Long,
            @RequestBody updateDto: MemberUpdateDto,
    ): MemberInfoDto {
        return memberService.updateMember(memberNo, updateDto)
    }

    @DeleteMapping("/{memberNo}")
    fun removeMember(
            @PathVariable memberNo: Long,
    ): Unit {
        return memberService.removeMember(memberNo)
    }
}