package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.error.ConflictException
import com.wct.model.CustomerRequest
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

    override fun createCustomer(request: CustomerRequest): CustomerEntity? {
        if (customerRepository.existsByEmail(request.email)) {
            return null
        }
        return customerRepository.save(request.toEntity())
    }

    private fun CustomerRequest.toEntity() =
        CustomerEntity(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email
        )
}