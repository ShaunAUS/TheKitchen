package com.example.kotlinPractice.domain.enums

import org.modelmapper.Converter

enum class LevelType(

        val position: String,
        val number: Int,
) {

    COMMI_CHEF("코미쉐프", 0),
    DEMI_CHEF("데미쉐프", 1),
    CHEF_DE_PARTIE("섹션장", 2),
    SOUS_CHEF("스쉐프", 3),
    HEAD_CHEF("헤드쉐프", 4);


    companion object {

        fun LEVELTYPE_TO_INT_CONVERTER(): Converter<LevelType, Int> =
                Converter { context -> context.source?.number }


        fun INT_TO_LEVELTYPE_CONVERTER(): Converter<Int, LevelType> =
                Converter { context ->
                    context.source?.let {
                        values().first() {
                            it.number == context.source
                        }
                    }
                }

    }
}