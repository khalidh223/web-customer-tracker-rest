package com.wct.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wct.domain.CustomerEntity
import com.wct.repository.CustomerRepository
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.properties.Gen
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import test.customerEntity
import test.customerPostRequest
import test.customerUpdateRequest
import test.toEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CustomerControllerComponentTests(
    @Autowired val context: WebApplicationContext,
    @Autowired val mapper: ObjectMapper,
    @Autowired val customerRepository: CustomerRepository
) {

    private val mvc: MockMvc = (MockMvcBuilders.webAppContextSetup(context).build())

    @BeforeEach
    fun setup() {
        customerRepository.deleteAll()
    }

    @Test
    fun `POST to createCustomer saves only the customer provided in the request`() {
        //given
        val request = Gen.customerPostRequest().random().first()

        //when
        mvc.perform(
            post("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
    }

    @Test
    fun `POST to createCustomer should return 409 Conflict if that customer already exists`() {
        //given
        val request = Gen.customerPostRequest().random().first()
        customerRepository.save(CustomerEntity(firstName = request.firstName, lastName = request.lastName, email = request.email))

        //when
        mvc.perform(
            post("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
    }

    @Test
    fun `PUT to updateCustomer should return 204 and update the customer found in the database with the information provided in the request`() {
        //given
        val newFirstName = "John"
        val savedCustomer = customerRepository.save(Gen.Companion.customerEntity().random().first())
        val request = Gen.customerUpdateRequest().random().first().copy(id = savedCustomer.id, firstName = newFirstName, lastName = savedCustomer.lastName, email = savedCustomer.email )

        //when
        mvc.perform(
            put("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
        customers.single() shouldBe request.toEntity()
    }

    @Test
    fun `PUT to updateCustomer should return 404 if customer id provided in the request is not found in the database`() {
        //given
        val newFirstName = "John"
        val savedCustomer = customerRepository.save(Gen.Companion.customerEntity().random().first())
        val request = Gen.customerUpdateRequest().random().first().copy(firstName = newFirstName)

        //when
        mvc.perform(
            put("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
        customers.single() shouldBe savedCustomer
    }
}