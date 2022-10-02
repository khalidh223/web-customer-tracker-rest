package test

import com.wct.domain.CustomerEntity
import com.wct.model.CustomerPostRequest
import com.wct.model.CustomerUpdateRequest
import io.kotest.property.Arb
import io.kotest.property.arbitrary.email
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.take
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
fun Gen.Companion.customerPostRequest() = object : Gen<CustomerPostRequest> {
    override fun constants() = emptyList<CustomerPostRequest>()

    override fun random(): Sequence<CustomerPostRequest> = generateSequence {
        CustomerPostRequest(
            firstName = "Khalid",
            lastName = "Hussain",
            email = randomEmail()
        )
    }
}
fun Gen.Companion.customerUpdateRequest() = object : Gen<CustomerUpdateRequest> {
    override fun constants() = emptyList<CustomerUpdateRequest>()

    override fun random(): Sequence<CustomerUpdateRequest> = generateSequence {
        CustomerUpdateRequest(
            id = Arb.long(1, Long.MAX_VALUE).next(),
            firstName = "Khalid",
            lastName = "Hussain",
            email = randomEmail()
        )
    }
}

fun CustomerUpdateRequest.toEntity() =
    CustomerEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )