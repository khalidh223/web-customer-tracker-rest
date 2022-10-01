package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.model.CustomerRequest
import org.apache.el.stream.Optional

interface CustomerService {
    fun getCustomers(): List<CustomerEntity>
    fun getCustomerById(customerId: Long): CustomerEntity?
    fun createCustomer(request: CustomerRequest): CustomerEntity?
}