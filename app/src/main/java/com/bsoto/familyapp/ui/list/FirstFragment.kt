package com.bsoto.familyapp.ui.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bsoto.familyapp.R
import com.bsoto.familyapp.core.Resource
import com.bsoto.familyapp.data.model.Product
import com.bsoto.familyapp.data.remote.FirebaseDataSource
import com.bsoto.familyapp.databinding.FragmentFirstBinding
import com.bsoto.familyapp.domain.repo.ProductRepoImpl
import com.bsoto.familyapp.presentation.ListViewModel
import com.bsoto.familyapp.presentation.ListViewModelFactory
import com.bsoto.familyapp.ui.list.adapter.ProductAdapter


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListViewModel> {
        ListViewModelFactory(
            ProductRepoImpl(
                FirebaseDataSource()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productList = listOf(Product())
        var adapter = ProductAdapter(productList)
        binding.floatNew.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.fetchProducts().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter = ProductAdapter(it.data)
                    binding.rvProductList.adapter = ProductAdapter(it.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        binding.rvProductList.adapter = ProductAdapter(productList)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                delete(adapter.getItem(viewHolder.absoluteAdapterPosition))
            }
        }).attachToRecyclerView(binding.rvProductList)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun delete(product: String ) {
      viewModel.deleteProduct(
          product
      ).observe(viewLifecycleOwner, { result ->
          when (result) {
              is Resource.Loading -> {
              }
              is Resource.Success -> {
                  Toast.makeText(requireContext(), "Producto Eliminado", Toast.LENGTH_SHORT).show()
              }
              is Resource.Failure -> {
                  Toast.makeText(
                      requireContext(),
                      "Ocurrio un error: ${result.exception}",
                      Toast.LENGTH_LONG
                  ).show()
              }
          }
      })
  }
}
