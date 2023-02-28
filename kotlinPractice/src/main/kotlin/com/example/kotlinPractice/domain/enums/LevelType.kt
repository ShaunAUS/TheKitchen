package com.example.kotlinPractice.domain.enums

enum class LevelType(

        val level: String,
        val number: Int,
) {

    COMMI_CHEF("코미쉐프", 0),
    DEMI_CHEF("데미쉐프", 1),
    CHEF_DE_PARTIE("섹션장", 2),
    SOUS_CHEF("스쉐프", 3),
    HEAD_CHEF("헤드쉐프", 4);

    companion object {
        fun typeToInt(levelType: LevelType): Int {
            return values().first { p -> p == levelType }.number
        }

        fun intToType(int: Int): LevelType {
            return values().first { t -> t.number == int }


        }
    }
}