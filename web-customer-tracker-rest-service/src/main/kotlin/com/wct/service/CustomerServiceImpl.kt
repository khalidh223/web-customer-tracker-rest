package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {
    override fun getCustomers(): List<CustomerEntity> {
        return customerRepository.findAll()
    }

    override fun getCustomerById(customerId: Long): CustomerEntity? {
        return customerRepository.findByIdOrNull(customerId)
    }
}