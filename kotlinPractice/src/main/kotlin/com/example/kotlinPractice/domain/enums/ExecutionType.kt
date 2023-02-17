package com.example.kotlinPractice.domain.enums

import org.modelmapper.Converter

enum class ExecutionType(
        val section: String,
        val number: Int,
) {
    BEFORE("시작전", 0),
    DONE("마무리", 1);

    companion object {

        fun EXECUTIONTYPE_TO_INT_CONVERTER(): Converter<ExecutionType, Int> =
                Converter{ context -> context.source.number }

        fun INT_TO_EXECUTIONTYPE_CONVERTER() : Converter<Int,ExecutionType> =
        Converter{
            context -> context.source?.let {
                values().first{
                    it.number == context.source
                }
        }
        }
    }

}