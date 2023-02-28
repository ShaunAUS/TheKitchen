package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.utils.ModelMapper
import jakarta.persistence.*


@Entity
class Member(

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var level: Int,

        @Column(nullable = false)
        var section: Int,

        @Column(nullable = false)
        var experience: Int,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        var kitchen: Kitchen,

        @OneToMany(mappedBy = "member", orphanRemoval = true)
        val preps: List<Prep>,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
) {

    fun update(updateDto: MemberUpdateDto) {
        this.name = updateDto.name
        this.level = LevelType.typeToInt(updateDto.level)
        this.section = SectionType.typeToInt(updateDto.section)
        this.experience = updateDto.experience
    }

    fun setupKitchen(kitchen: Kitchen) {
        this.kitchen = kitchen
    }

    companion object {
        fun of(memberCreateDto: MemberCreateDto,kitchen: Kitchen): Member {
            return Member(
                    name = memberCreateDto.name,
                    level = LevelType.typeToInt(memberCreateDto.level),
                    section = SectionType.typeToInt(memberCreateDto.section),
                    experience = memberCreateDto.experience,
                    kitchen = kitchen,
                    preps = emptyList(),//TODO  ModelMapper Converter는 빈값 안넣어줘도 됐는데.. 이방법은 어떻게 해결할까
                    id = null,      //TODO 이렇게 null 세팅해줘야하는가..?
                    )
        }
    }


}

