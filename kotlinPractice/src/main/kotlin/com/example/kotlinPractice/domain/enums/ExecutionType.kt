package com.example.kotlinPractice.domain.enums

import org.modelmapper.Converter

enum class ExecutionType(
        val execution: String,
        val number: Int,
) {
    BEFORE("시작전", 0),
    DONE("마무리", 1);

    companion object {

        fun typeToInt(executionType: ExecutionType) : Int{
            return values().first {p -> p == executionType}.number
        }
        fun intToType(int: Int) : ExecutionType{
            return values().first {p -> p.number == int}
        }
    }

}