package test

import com.wct.domain.CustomerEntity
import com.wct.model.CustomerRequest
import io.kotest.property.Arb
import io.kotest.property.arbitrary.email
import io.kotest.property.arbitrary.next
import io.kotlintest.properties.Gen

private fun randomEmail() = Arb.email().next()

fun Gen.Companion.customerEntity() = object : Gen<CustomerEntity> {
    override fun constants() = emptyList<CustomerEntity>()

    override fun random(): Sequence<CustomerEntity> = generateSequence {
        CustomerEntity(
            firstName = "Khalid",
            lastName = "Hussain",
            email = randomEmail()
        )
    }
}
fun Gen.Companion.customerRequest() = object : Gen<CustomerRequest> {
    override fun constants() = emptyList<CustomerRequest>()

    override fun random(): Sequence<CustomerRequest> = generateSequence {
        CustomerRequest(
            firstName = "Khalid",
            lastName = "Hussain",
            email = randomEmail()
        )
    }
}