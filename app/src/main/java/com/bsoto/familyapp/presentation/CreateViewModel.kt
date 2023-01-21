package com.bsoto.familyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.domain.repo.ProductRepo
import kotlinx.coroutines.Dispatchers

class CreateViewModel(
    private val repo: ProductRepo
): ViewModel() {

    fun createProduct(name: String, comment: String, quantity: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource
                .Success(repo.newProduct(name,comment,quantity)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}





class CreateViewModelFactory(private val repo: ProductRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepo::class.java).newInstance(repo)
    }
}