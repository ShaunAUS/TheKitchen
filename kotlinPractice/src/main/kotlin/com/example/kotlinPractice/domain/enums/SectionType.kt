package com.example.kotlinPractice.domain.enums

import org.modelmapper.Converter

enum class SectionType(

        val section: String,
        val number: Int,
) {

    COLD("콜드섹션", 0),
    HOT("핫섹션", 1),
    PASTRY("디저트", 2);

    companion object {

        fun SECTIONTYPE_TO_INT_CONVERTER(): Converter<SectionType, Int> =
                Converter { context -> context.source?.number }


        fun INT_TO_SECTIONTYPE_CONVERTER(): Converter<Int, SectionType> =
                Converter{
                    context -> context.source?.let {
                        values().first{
                            it.number == context.source
                        }
                }
                }
    }
}