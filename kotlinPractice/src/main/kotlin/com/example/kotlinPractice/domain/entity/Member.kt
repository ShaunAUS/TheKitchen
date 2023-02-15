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
        val name: String,

        @Column(nullable = false)
        val level: LevelType,

        @Column(nullable = false)
        val section: SectionType,

        @Column(nullable = false)
        val experience: Int,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen,

        @OneToMany(mappedBy = "member",  orphanRemoval = true)
        val preps: List<Prep>,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
) {
    @PrePersist
    fun prePersist() {
        this.experience ?: 0
    }

    fun update(updateDto: MemberUpdateDto) {
        ModelMapper.getMapper()
                .map(this, updateDto)
    }

    companion object{
        fun of(memberCreateDto: MemberCreateDto): Member {
            return ModelMapper.getMapper()
                    .map(memberCreateDto, Member::class.java)
        }
    }


}