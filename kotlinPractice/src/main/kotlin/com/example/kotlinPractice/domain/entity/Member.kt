package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.member.MemberCreateDto
import com.example.kotlinPractice.domain.dto.member.MemberUpdateDto
import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import com.example.kotlinPractice.utils.ModelMapper
import jakarta.persistence.*
import org.modelmapper.TypeMap
import kotlin.reflect.KProperty1


@Entity
class Member(

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        var level: Int,

        @Column(nullable = false)
        var section: Int,

        @Column(nullable = false)
        val experience: Int,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        var kitchen: Kitchen,

        @OneToMany(mappedBy = "member", orphanRemoval = true)
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

    fun setupKitchen(kitchen: Kitchen) {
        this.kitchen = kitchen
    }

    fun convertLevel(level: Int) {
        this.level = level
    }
    fun convertSection(section: Int) {
        this.section = section
    }

    //TODO converter section, level
    companion object {
        fun of(memberCreateDto: MemberCreateDto): Member {
            return ModelMapper.getMapper()
                    .typeMap(MemberCreateDto::class.java, Member::class.java)
                    .addMappings { mapper ->
                        mapper.using(LevelType.LEVELTYPE_TO_INT_CONVERTER())
                                .map(MemberCreateDto::level, Member::convertLevel)   //TODO need to fix
                        mapper.using(SectionType.SECTIONTYPE_TO_INT_CONVERTER())
                                .map(MemberCreateDto::section, Member::convertSection)
                    }
                    .map(memberCreateDto)


        }
    }


}

