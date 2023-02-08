package com.example.kotlinPractice.domain.enums

enum class SectionType(

        val section:String,
        val number:Int
)  {

    COLD("콜드섹션",0),
    HOT("핫섹션",1),
    PASTRY("디저트",2)

}