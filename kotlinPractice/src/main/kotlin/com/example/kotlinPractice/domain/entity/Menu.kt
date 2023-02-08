package com.example.kotlinPractice.domain.entity

import jakarta.persistence.*

@Entity
class Menu(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false)
        val price: Int,

        @Column(nullable = false)
        val alergicType: Int,

        @ManyToOne
        @JoinColumn(name = "kitchen_id")
        val kitchen: Kitchen
) {
}