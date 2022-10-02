package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.error.ConflictException
import com.wct.error.CustomerNotFoundException
import com.wct.model.CustomerPostRequest
import com.wct.model.CustomerUpdateRequest
import com.wct.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService {
    override fun getCustomers(): List<CustomerEntity> {
        return customerRepository.findAll()
    }

    override fun getCustomerById(customerId: Long): CustomerEntity? {
        return customerRepository.findByIdOrNull(customerId) ?: throw CustomerNotFoundException("Customer id $customerId not found")
    }

    override fun createCustomer(request: CustomerPostRequest): CustomerEntity? {
        if (customerRepository.existsByEmail(request.email)) {
            throw ConflictException("Customer with email ${request.email} already exists")
        }
        return customerRepository.save(request.toEntity())
    }

    override fun updateCustomer(request: CustomerUpdateRequest): CustomerEntity? {
        if (!customerRepository.existsById(request.id)) {
            throw CustomerNotFoundException("Customer id ${request.id} not found")
        }
       return customerRepository.save(request.toEntity())
    }

    private fun CustomerPostRequest.toEntity() =
        CustomerEntity(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email
        )

    private fun CustomerUpdateRequest.toEntity() =
        CustomerEntity(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email
        )
}