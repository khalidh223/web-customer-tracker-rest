package com.wct.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wct.domain.CustomerEntity
import com.wct.repository.CustomerRepository
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.properties.Gen
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import test.customerRequest

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
        val request = Gen.customerRequest().random().first()

        //when
        mvc.perform(
            post("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
    }
    @Test
    fun `POST to createCustomer should return 405 Conflict if that customer already exists`() {
        //given
        val request = Gen.customerRequest().random().first()
        customerRepository.save(CustomerEntity(firstName = request.firstName, lastName = request.lastName, email = request.email))

        //when
        mvc.perform(
            post("/customers").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict)

        //then
        val customers = customerRepository.findAll()
        customers shouldHaveSize 1
    }
}