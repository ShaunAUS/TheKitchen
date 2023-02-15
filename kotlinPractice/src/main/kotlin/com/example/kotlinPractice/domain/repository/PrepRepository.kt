package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.entity.Prep
import org.springframework.data.jpa.repository.JpaRepository

interface PrepRepository :JpaRepository<Prep,Long> {
}