package com.wct.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomerRestExceptionHandler {
    // add an exception handler for CustomerNotFoundException

    @ExceptionHandler
    fun handleException(exc: CustomerNotFoundException): ResponseEntity<CustomerErrorResponse> {
        // create a CustomerErrorResponse
        val error = CustomerErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exc.message,
            timestamp = System.currentTimeMillis()
        )
        // return ResponseEntity
        return ResponseEntity<CustomerErrorResponse>(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleException(exc: ConflictException): ResponseEntity<CustomerErrorResponse> {
        // create a CustomerErrorResponse
        val error = CustomerErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = exc.message,
            timestamp = System.currentTimeMillis()
        )
        // return ResponseEntity
        return ResponseEntity<CustomerErrorResponse>(error, HttpStatus.CONFLICT)
    }

}