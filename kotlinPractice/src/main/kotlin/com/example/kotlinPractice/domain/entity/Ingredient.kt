package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Ingredient(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val buyDate: LocalDateTime,

        @Column(nullable = false)
        val expireDate: LocalDateTime,

        //TODO 항상최신화
        @Column(nullable = false)
        val expirationPeriod: LocalDateTime,

        @Column(nullable = false)
        val quantity: Int,

        val priority: Int?,

        @ManyToOne
        @JoinColumn(name = "menu_id")
        val menu: Menu,

        @ManyToOne
        @JoinColumn(name = "refrigerator_id")
        val refrigerator: Refrigerator,


        ) {

    @PrePersist
    fun prePersist9() {
        this.priority ?: 0
    }
}