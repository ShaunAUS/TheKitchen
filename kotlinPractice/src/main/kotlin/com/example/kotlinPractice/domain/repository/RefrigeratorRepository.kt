package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.entity.Refrigerator
import org.springframework.data.jpa.repository.JpaRepository

interface RefrigeratorRepository :JpaRepository<Refrigerator,Long>{
}