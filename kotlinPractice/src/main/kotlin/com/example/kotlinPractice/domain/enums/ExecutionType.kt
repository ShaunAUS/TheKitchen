package com.example.kotlinPractice.domain.enums

enum class ExecutionType(
        val section:String,
        val number:Int
) {
    BEFORE("시작전",0),
    DONE("마무리",1)
}