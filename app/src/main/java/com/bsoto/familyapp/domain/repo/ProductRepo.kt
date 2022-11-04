package com.bsoto.familyapp.domain.repo

import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.model.Product

interface ProductRepo {
    suspend fun getProducts(): Resource<List<Product>>
    suspend fun newProduct(name: String, comment: String, quantity: Int)
    suspend fun deleteProduct(id: String)
}