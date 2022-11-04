package com.bsoto.familyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.domain.repo.ProductRepo
import kotlinx.coroutines.Dispatchers

class ListViewModel(
    private val repo: ProductRepo
): ViewModel() {

    fun fetchProducts() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getProducts())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteProduct(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource
                .Success(repo.deleteProduct(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class ListViewModelFactory(private val repo: ProductRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepo::class.java).newInstance(repo)
    }
}