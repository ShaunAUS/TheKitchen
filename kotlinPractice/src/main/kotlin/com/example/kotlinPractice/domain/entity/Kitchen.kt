package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*

@Entity
class Kitchen(

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val location: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
) {
}