package com.wct.service

import com.wct.domain.CustomerEntity
import com.wct.model.CustomerUpdateRequest
import com.wct.repository.CustomerRepository
import io.kotest.property.Arb
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotlintest.IsolationMode
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import test.customerEntity
import test.customerPostRequest
import io.kotlintest.shouldBe
import test.customerUpdateRequest
import test.toEntity

class CustomerServiceImplTests : StringSpec({
    val repository = mockk<CustomerRepository>()
    val service: CustomerService = CustomerServiceImpl(repository)

    "getCustomers should call repository findAll method" {
        //arrange
        val customerEntity = Gen.customerEntity().random().first()
        val savedCustomerEntity = customerEntity.copy(id = 1)

        every {
            repository.save(customerEntity)
        } returns savedCustomerEntity

        every {
            repository.findAll()
        } returns listOf(customerEntity)

        //act
        service.getCustomers()

        //assert
        verify(exactly = 1) {
            repository.findAll()
        }
    }

    "createCustomer should save a customer to the customer table" {
        //arrange
        val customerRequest = Gen.customerPostRequest().random().first()
        val customerEntity = CustomerEntity(
            firstName = customerRequest.firstName,
            lastName = customerRequest.lastName,
            email = customerRequest.email
        )
        val savedCustomerEntity = customerEntity.copy(id = Arb.long(0, Long.MAX_VALUE).next())

        every {
            repository.existsByEmail(savedCustomerEntity.email)
        } returns false

        every {
            repository.save(customerEntity)
        } returns savedCustomerEntity

        //act
        service.createCustomer(customerRequest)

        //assert
        verify(exactly = 1) {
            repository.save(customerEntity)
        }
    }

}) {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf
}