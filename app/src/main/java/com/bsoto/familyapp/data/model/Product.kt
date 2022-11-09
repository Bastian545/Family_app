package com.bsoto.familyapp.data.model

import com.google.firebase.firestore.Exclude

data class Product(
    @Exclude
    var id: String ="",
    val name: String ="",
    val comment: String = "",
    val quantity: Int = 0,
    val check: Boolean = false
)

data class ProductList ( val result: List<Product> = listOf())