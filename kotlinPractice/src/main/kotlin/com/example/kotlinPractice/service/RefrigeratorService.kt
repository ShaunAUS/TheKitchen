package com.example.kotlinPractice.service

import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorCreateDto
import com.example.kotlinPractice.domain.dto.refrigerator.RefrigeratorInfoDto

interface RefrigeratorService {
    fun getRefrigerator(refrigeratorId: Long): RefrigeratorInfoDto
    fun remove(refrigeratorId: Long)
    fun createRefrigerator(refrigeratorCreateDto: RefrigeratorCreateDto,kitchenId:Long): RefrigeratorInfoDto
}