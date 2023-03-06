package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.enums.ExecutionType
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Prep(


        @Column(nullable = false)
        val job: String,

        @Column(nullable = false)
        val deadLine: LocalDate,

        @Column(nullable = false)
        var executionStatus: Int,

        @Column(nullable = false)
        val priority: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        var member: Member,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,


        ) {

    fun updatePrepStatus() {
        this.executionStatus = ExecutionType.DONE.number
    }


    companion object {
        fun of(prepCreateDto: PrepCreateDto, targetMember: Member): Prep {
            return Prep(
                    id = null,
                    job = prepCreateDto.job,
                    deadLine = prepCreateDto.deadLine,
                    executionStatus = ExecutionType.typeToInt(prepCreateDto.executionType),
                    priority = prepCreateDto.priority,
                    member = targetMember
            )
        }
    }

}