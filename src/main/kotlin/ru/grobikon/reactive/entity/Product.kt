package ru.grobikon.reactive.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
data class Product(
    @Id
    var id: String? = null,
    var name: String? = null,
    var qty: Int? = null,
    var price: Double? = null
)
