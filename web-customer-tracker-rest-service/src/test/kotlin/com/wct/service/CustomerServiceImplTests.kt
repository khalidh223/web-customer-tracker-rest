package com.wct.service

import com.wct.repository.CustomerRepository
import io.kotlintest.IsolationMode
import io.kotlintest.properties.Gen
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import test.customerEntity

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
}) {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf
}