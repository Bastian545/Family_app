package com.bsoto.familyapp.domain.repo

import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.model.Product
import com.bsoto.familyapp.data.remote.FirebaseDataSource

class ProductRepoImpl(
    private val firebaseDataSource: FirebaseDataSource
): ProductRepo {
    override suspend fun getProducts(): Resource<List<Product>> = firebaseDataSource.getProducts()
    override suspend fun newProduct(name: String,comment: String, quantity: Int) {
    firebaseDataSource.newProduct(name,comment,quantity)
    }
    override suspend fun deleteProduct(id: String) {
        firebaseDataSource.deleteProduct(id)
    }

}

