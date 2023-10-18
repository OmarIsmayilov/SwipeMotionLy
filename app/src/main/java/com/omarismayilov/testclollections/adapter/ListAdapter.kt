package com.omarismayilov.testclollections.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omarismayilov.testclollections.Product
import com.omarismayilov.testclollections.R
import com.omarismayilov.testclollections.databinding.ItemProductBinding

class ListAdapter(
    var onClick: (Int) -> Unit,
    var onSwipe: (Int) -> Unit,
    var onComplete: () -> Unit
) :
    RecyclerView.Adapter<ListAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val motionLayout: MotionLayout = binding.motionLayout
        fun bind(item:Product) {
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.description
                tvPrice.text = item.price.toString()
            }
            motionLayout.transitionToStart()
            binding.materialCardView2.setOnClickListener {
                onClick(adapterPosition)
                Log.e("VIEW_HOLDER", "Holder item clicked")
            }
            motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {

                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    onSwipe(adapterPosition)
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {}

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    onComplete()
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    object diffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallBack)


}