package com.example.kotlinPractice.server.controller

import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto
import com.example.kotlinPractice.service.RefrigeratorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/refrigerator")
class RefrigeratorController(
        private val refrigeratorService: RefrigeratorService
) {

    @GetMapping("/{refrigeratorId}")
    fun getRefrigeratorStatus(
            @PathVariable refrigeratorId: Long,
    ): RefrigeratorInfoDto {
        return refrigeratorService.getRefrigerator(refrigeratorId)
    }

    @PostMapping("")
    fun createRefrigerator(
            @RequestBody refrigeratorCreateDto: RefrigeratorCreateDto
    ): RefrigeratorInfoDto {
        return refrigeratorService.registerRefrigerators(refrigeratorCreateDto)
    }

    @DeleteMapping("/{refrigeratorId}")
    fun removeRefrigerator(
            @PathVariable refrigeratorId: Long,
    ){
        refrigeratorService.remove(refrigeratorId)
    }

}