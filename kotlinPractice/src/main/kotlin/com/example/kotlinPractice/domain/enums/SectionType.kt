package com.example.kotlinPractice.domain.enums

enum class SectionType(

        val section: String,
        val number: Int,
) {

    COLD("콜드섹션", 0),
    HOT("핫섹션", 1),
    PASTRY("디저트", 2);

    companion object {
        fun typeToInt(section: SectionType): Int {
            return values().first { p -> p == section }.number
        }
        fun intToType(int: Int): SectionType {
            return values().first { t -> t.number == int }
        }
    }

}