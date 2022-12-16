package io.spring.scs_kotlin_kstream_sample.dto.customer

import java.util.UUID

data class Customer(
    val uuid: UUID,
    val name: String,
    val type: Type,
    val active: Boolean,
    val country: String
)