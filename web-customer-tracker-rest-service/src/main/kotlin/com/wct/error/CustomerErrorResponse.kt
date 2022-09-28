package com.wct.error

data class CustomerErrorResponse(
    val status: Int,
    val message: String?,
    val timestamp: Long
)