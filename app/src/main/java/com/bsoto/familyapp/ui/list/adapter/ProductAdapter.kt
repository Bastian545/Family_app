package com.bsoto.familyapp.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bsoto.familyapp.core.BaseViewHolder
import com.bsoto.familyapp.data.model.Product
import com.bsoto.familyapp.databinding.ProductItemViewBinding

class ProductAdapter(private val productList: MutableList<Product>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bind(productList[position])
        }
    }

    override fun getItemCount(): Int = productList.size

    fun getItem(position: Int): String {
        if (position < productList.size) {
            return productList.get(position).id
        }
        return productList.get(position-1).id
    }

    fun removeFromList(position: Int){
        productList.removeAt(position)
        notifyItemRemoved(position)
    }

    private inner class ProductViewHolder(
        val binding: ProductItemViewBinding
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            if(item.quantity != 0){binding.rvQuantity.text = item.quantity.toString()}
            binding.rvName.text = item.name
            binding.rvComment.text = item.comment
        }
    }
}