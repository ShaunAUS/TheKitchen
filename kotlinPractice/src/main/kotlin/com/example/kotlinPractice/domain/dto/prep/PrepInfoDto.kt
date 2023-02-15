package com.example.kotlinPractice.domain.dto.prep

import com.example.kotlinPractice.domain.entity.Prep
import com.example.kotlinPractice.domain.enums.ExecutionType
import com.example.kotlinPractice.utils.ModelMapper
import java.time.LocalDate

data class PrepInfoDto(
        val job:String,
        val priority:Int,
        val executionStatus: ExecutionType,
        val executionDate: LocalDate
) {
    //TODO need Converter
    companion object {
        fun of(prepById: Prep): PrepInfoDto {
            return ModelMapper.getMapper()
                    .map(prepById, PrepInfoDto::class.java)
        }
    }
}