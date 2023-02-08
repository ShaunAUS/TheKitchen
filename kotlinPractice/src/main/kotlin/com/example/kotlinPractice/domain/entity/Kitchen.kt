package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*

@Entity
class Kitchen(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val location: String,
) {
}