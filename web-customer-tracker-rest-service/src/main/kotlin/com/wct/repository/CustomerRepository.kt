package com.wct.repository

import com.wct.domain.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long>{
    fun existsByEmail(email: String): Boolean
}