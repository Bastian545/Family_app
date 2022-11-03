package com.bsoto.familyapp.data.model

data class Product(
    val name: String ="",
    val comment: String = "",
    val quantity: Int = 0,
    val check: Boolean = false
)

data class ProductList ( val result: List<Product> = listOf())