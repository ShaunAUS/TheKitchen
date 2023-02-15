package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
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
    ): MemberWithPrepInfoDto {

        return memberService.register(memberCreateDto);
    }


    @PostMapping("/all")
    fun getMembers(
            @PageableDefault(sort = ["member_id"], direction = Sort.Direction.DESC, size = 10) pageable: Pageable,
    ): Page<MemberWithPrepInfoDto> {
        return memberService.getMembers(pageable);

    }

    @GetMapping("/{memberId}")
    fun getMember(
            @PathVariable memberId: Long,
    ): MemberWithPrepInfoDto {
        return memberService.getMember(memberId)
    }

    @PatchMapping("/{memberId}")
    fun updateMember(
            @PathVariable memberId: Long,
            @RequestBody updateDto: MemberUpdateDto,
    ): MemberWithPrepInfoDto {
        return memberService.updateMember(memberId, updateDto)
    }

    @DeleteMapping("/{memberId}")
    fun removeMember(
            @PathVariable memberId: Long,
    ): Unit {
        return memberService.removeMember(memberId)
    }

    //다른 멤버에게  프렙리스트를 주는 형식
    @PostMapping("/{targetMemberId}/prep")
    fun makePrep(
            @PathVariable targetMemberId: Long,
            @RequestBody prepCreateDtoList: List<PrepCreateDto>
    ) : MemberWithPrepInfoDto{
        return memberService.makePrep(targetMemberId,prepCreateDtoList)
    }

    @PatchMapping("/{prepId}")
    fun finishPrep(
            @PathVariable prepId: Long
    ) : PrepInfoDto{
        return memberService.updatePrepStatus(prepId)
    }

    @GetMapping("/{memberId}/prep")
    fun checkMyPrep(
            @PathVariable memberId: Long
    ) : MemberWithPrepInfoDto{
        memberService.getMyPrep(memberId)
    }
}