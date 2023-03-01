package com.example.kotlinPractice.domain.enums

enum class AlergicType(

        val alergic: String,
        val number: Int,
) {

    SESAME("깨", 0),
    CAPSICUM("고추", 1),
    PEANUT("땅콩", 2);


    companion object {

        fun typeToInt(alergictype: AlergicType): Int {
            return AlergicType.values().first { p -> p == alergictype }.number
        }

        fun intToType(int: Int): AlergicType {
            return AlergicType.values().first { p -> p.number == int }
        }
    }


}