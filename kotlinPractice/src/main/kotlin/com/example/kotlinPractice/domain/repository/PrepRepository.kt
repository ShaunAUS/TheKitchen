package com.example.kotlinPractice.domain.repository

import com.example.kotlinPractice.domain.dto.member.MemberWithPrepInfoDto
import com.example.kotlinPractice.domain.entity.Prep
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PrepRepository :JpaRepository<Prep,Long> {


}