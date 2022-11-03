package com.bsoto.familyapp.data.remote

import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDataSource {
    suspend fun getProducts(): Resource<List<Product>>{
        val productList = mutableListOf<Product>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("products").get().await()
        for(product in querySnapshot.documents){
            product.toObject(Product::class.java)?.let { it ->
                productList.add(it)
            }
        }
        return Resource.Success(productList)
    }
}