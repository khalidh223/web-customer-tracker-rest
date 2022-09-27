package com.wct

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebCustomerTrackerRestApplication

fun main(args: Array<String>) {
	runApplication<WebCustomerTrackerRestApplication>(*args)
}
