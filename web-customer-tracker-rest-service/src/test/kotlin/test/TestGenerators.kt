package test

import com.wct.domain.CustomerEntity
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotlintest.properties.Gen

fun Gen.Companion.customerEntity() = object : Gen<CustomerEntity> {
    override fun constants() = emptyList<CustomerEntity>()

    override fun random(): Sequence<CustomerEntity> = generateSequence {
        CustomerEntity(
            firstName = "hello",
            lastName = "good",
            email = "khalid@gmail.com"
        )
    }
}