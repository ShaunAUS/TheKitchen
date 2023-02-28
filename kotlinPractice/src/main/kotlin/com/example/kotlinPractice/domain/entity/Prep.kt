package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.enums.ExecutionType
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Prep(


        @Column(nullable = false)
        val job: String,

        @Column(nullable = false)
        val executionDate: LocalDate,

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
                    executionDate = prepCreateDto.executionDate,
                    executionStatus = ExecutionType.typeToInt(prepCreateDto.executionType),
                    priority = prepCreateDto.priority,
                    member = targetMember
            )
        }
    }

}