package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*

@Entity
class Refrigerator(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen
) {

}