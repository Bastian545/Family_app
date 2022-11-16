package com.bsoto.familyapp.data.model

import androidx.room.Entity
import com.google.firebase.firestore.Exclude
@Entity
data class Product(
    @Exclude
    var id: String ="",
    val name: String ="",
    val comment: String = "",
    val quantity: Int = 0,
    val check: Boolean = false
)

data class ProductList ( val result: List<Product> = listOf())