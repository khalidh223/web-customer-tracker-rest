package com.wct.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "customer")
data class CustomerEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = -1,

    @Column(name = "first_name")
    val firstName: String,

    @Column(name = "last_name")
    val lastName: String,

    @Column(name = "email")
    val email: String
)