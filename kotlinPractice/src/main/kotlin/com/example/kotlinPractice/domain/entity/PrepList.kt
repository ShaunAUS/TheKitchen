package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class PrepList(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val job: String,

        @Column(nullable = false)
        val executionDate: LocalDateTime,

        @Column(nullable = false)
        val executionStatus: Boolean,

        @Column(nullable = false)
        val priority: Int,

        @ManyToOne
        @JoinColumn(name = "member_id")
        val member: Member

        ) {

}