package com.wct.model

import javax.validation.constraints.Email

data class CustomerPostRequest(
    val firstName: String,
    val lastName: String,
    @field:Email
    val email: String
)

data class CustomerUpdateRequest(
    val id: Long,
    val firstName: String,
    val lastName: String,
    @field:Email
    val email: String
)