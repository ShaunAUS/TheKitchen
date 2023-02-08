package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.enums.LevelType
import com.example.kotlinPractice.domain.enums.SectionType
import jakarta.persistence.*
import javax.print.attribute.IntegerSyntax

@Entity
class Member(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val level: LevelType,

        @Column(nullable = false)
        val section: SectionType,

        val experience: Int?,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen
) {
    @PrePersist
    fun prePersist(){
        this.experience?:0
    }


}