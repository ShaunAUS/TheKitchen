package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long>
