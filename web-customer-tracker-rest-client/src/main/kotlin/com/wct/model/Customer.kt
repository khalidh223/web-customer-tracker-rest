package com.wct.model

import javax.validation.constraints.Email

data class CustomerRequest(
    val firstName: String,
    val lastName: String,
    @field:Email
    val email: String
)