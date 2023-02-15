package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.entity.Kitchen
import org.springframework.data.jpa.repository.JpaRepository

interface KitchenRepository: JpaRepository<Kitchen, Long>
