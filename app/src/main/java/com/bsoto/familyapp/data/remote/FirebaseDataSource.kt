package com.bsoto.familyapp.data.remote

import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.model.Product
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.util.*

class FirebaseDataSource {
    suspend fun getProducts(): Resource<MutableList<Product>> {
        val productList = mutableListOf<Product>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("products").get().await()
        for (product in querySnapshot.documents) {
            product.toObject(Product::class.java)?.let {
                it.apply {
                    id = product.id
                }
                productList.add(it)
            }
        }
        return Resource.Success(productList)
    }

    suspend fun newProduct(name: String, comment: String, quantity: Int) {
        FirebaseFirestore.getInstance().collection("products").add(
            Product(

                name = name,
                comment = comment,
                quantity = quantity,
                check = false
            )
        ).await()
    }

    suspend fun deleteProduct(id: String) {
        FirebaseFirestore.getInstance().collection("products").document(id).delete().await()
    }
}