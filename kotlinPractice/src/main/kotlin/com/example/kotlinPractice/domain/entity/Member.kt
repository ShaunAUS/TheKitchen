package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.group.libraryapp.utils.getModelMapper
import jakarta.persistence.*

@Entity
class Member(

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val level: LevelType,

        @Column(nullable = false)
        val section: SectionType,

        val experience: Int?,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
) {
    @PrePersist
    fun prePersist() {
        this.experience ?: 0
    }

    fun update(updateDto: MemberUpdateDto) {
        getModelMapper().map(updateDto, this)
    }

    companion object{
        fun of(memberCreateDto: MemberCreateDto): Member {
            TODO("Not yet implemented")
        }
    }


}