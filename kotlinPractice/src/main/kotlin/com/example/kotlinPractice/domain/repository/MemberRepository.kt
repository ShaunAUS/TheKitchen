package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member,Long> {
}