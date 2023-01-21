package com.bsoto.familyapp.ui.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bsoto.familyapp.R
import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.remote.FirebaseDataSource
import com.bsoto.familyapp.databinding.FragmentSecondBinding
import com.bsoto.familyapp.domain.repo.ProductRepoImpl
import com.bsoto.familyapp.presentation.CreateViewModel
import com.bsoto.familyapp.presentation.CreateViewModelFactory

class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding

    private val viewModel by viewModels<CreateViewModel> {
        CreateViewModelFactory(
            ProductRepoImpl(
                FirebaseDataSource()
            )
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
        binding.btnAdd.setOnClickListener {
            val name = binding.txtProduct.text.toString().trim()
            val comment = binding.txtComment.text.toString().trim()
            val quantity = try {
                binding.txtQuantity.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            if (name.equals("")) {
                Toast.makeText(requireContext(), "Ingrese Nombre del Producto", Toast.LENGTH_SHORT)
                    .show()
                    binding.txtProduct.requestFocus()
            } else {
                viewModel.createProduct(
                    name, comment, quantity
                ).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                            Toast.makeText(requireContext(), "Producto Agregado", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "Ocurrio un error: ${result.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }


        }

    }


}
