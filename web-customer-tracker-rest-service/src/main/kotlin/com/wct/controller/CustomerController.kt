package com.wct.controller

import com.wct.domain.CustomerEntity
import com.wct.error.CustomerNotFoundException
import com.wct.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(private val customerService: CustomerService) {

    @GetMapping()
    fun getCustomers(): List<CustomerEntity> {
        return customerService.getCustomers()
    }

    @GetMapping("{customerId}")
    fun getCustomerById(@PathVariable customerId: Long): CustomerEntity? {
        return customerService.getCustomerById(customerId) ?: throw CustomerNotFoundException("Customer id $customerId not found")
    }
}