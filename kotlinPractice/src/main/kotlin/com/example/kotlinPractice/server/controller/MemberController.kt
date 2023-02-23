package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/member")
class MemberController(

        private val memberService: MemberService,
) {


    @PostMapping("")
    fun createMember(
            @RequestBody memberCreateDto: MemberCreateDto,
            @RequestParam("kitchenId") kitchenId: Long,
    ): MemberInfoDto {
        return memberService.createMember(memberCreateDto,kitchenId);
    }


    @GetMapping("/all")
    fun getMembers(
            @PageableDefault(sort = ["member_id"], direction = Sort.Direction.DESC, size = 10) pageable: Pageable,
    ): Page<MemberInfoDto> {
        return memberService.getMembers(pageable);

    }

    @GetMapping("/{targetMemberId}")
    fun getMember(
            @PathVariable targetMemberId: Long,
    ): MemberInfoDto {
        return memberService.getMember(targetMemberId)
    }

    @PatchMapping("/{targetMemberId}")
    fun updateMember(
            @PathVariable targetMemberId: Long,
            @RequestBody updateDto: MemberUpdateDto,
    ): MemberInfoDto {
        return memberService.updateMember(targetMemberId, updateDto)
    }

    @DeleteMapping("/{targetMemberId}")
    fun removeMember(
            @PathVariable targetMemberId: Long,
    ) {
        return memberService.removeMember(targetMemberId)
    }


}