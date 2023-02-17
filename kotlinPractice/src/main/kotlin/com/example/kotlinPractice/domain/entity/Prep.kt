package com.example.kotlinPractice.domain.entity

import com.example.kotlinPractice.domain.dto.prep.PrepCreateDto
import com.example.kotlinPractice.domain.enums.ExecutionType
import com.example.kotlinPractice.utils.ModelMapper
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Prep(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val job: String,

        @Column(nullable = false)
        val executionDate: LocalDateTime,

        @Column(nullable = false)
        var executionStatus: Int,

        @Column(nullable = false)
        val priority: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        var member: Member

        ) {
    fun updateExecutionMember(targetMember: Member) :Prep{
        this.member = targetMember
        return this
    }

    fun updatePrepStatus() {
        this.executionStatus = ExecutionType.DONE.number
    }

    //Todo ExcutionType Convert
    companion object {
        fun of(prepCreateDto: PrepCreateDto) :Prep{
            return ModelMapper.getMapper()
                    .map(prepCreateDto, Prep::class.java)
        }
    }

}