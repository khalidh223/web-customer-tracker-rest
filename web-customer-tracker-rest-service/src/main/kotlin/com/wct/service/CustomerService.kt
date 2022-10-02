package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.model.CustomerPostRequest
import com.wct.model.CustomerUpdateRequest

interface CustomerService {
    fun getCustomers(): List<CustomerEntity>
    fun getCustomerById(customerId: Long): CustomerEntity?
    fun createCustomer(request: CustomerPostRequest): CustomerEntity?
    fun updateCustomer(request: CustomerUpdateRequest): CustomerEntity?
}