package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.dto.prep.PrepInfoDto
import com.example.kotlinPractice.service.PrepService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/prep")
class PrepController(
        private val prepService : PrepService
) {

    //다른 멤버에게  프렙리스트를 주는 형식
    @PostMapping("")
    fun makePrep(
            @RequestParam targetMemberId: Long,
            @RequestBody prepCreateDtos: List<PrepCreateDto>
    ) : MemberWithPrepInfoDto {
        return prepService.createPrepToTargetMember(targetMemberId,prepCreateDtos)
    }

    @PatchMapping("/{prepId}")
    fun finishPrep(
            @PathVariable prepId: Long
    ) : PrepInfoDto {
        return prepService.updatePrepStatus(prepId)
    }

    /*@GetMapping("/prep")
    fun checkMyPrep(
            @RequestParam targetMemberId: Long,
    ) : MemberWithPrepInfoDto {
        return prepService.getMyPrep(targetMemberId)
    }*/

}