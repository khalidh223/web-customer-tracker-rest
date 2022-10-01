package com.wct.controller

import com.wct.domain.CustomerEntity
import com.wct.error.ConflictException
import com.wct.error.CustomerNotFoundException
import com.wct.model.CustomerRequest
import com.wct.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@Valid @RequestBody request: CustomerRequest): CustomerEntity {
        return customerService.createCustomer(request) ?: throw ConflictException("Customer with email ${request.email} already exists")
    }
}