package com.omarismayilov.testclollections.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.omarismayilov.testclollections.BaseFragment
import com.omarismayilov.testclollections.Product
import com.omarismayilov.testclollections.adapter.ListAdapter
import com.omarismayilov.testclollections.databinding.FragmentListBinding


class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    lateinit var adapter: ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = List(10) {
            Product("${it+1} .product", "Lorem ipsum sit iitvLorem ipsum sitLorem ipsum sitLorem ipsum sitLorem ipsum sitLorem ipsum sit", it+1 * 15.5)
        }


        adapter =
            ListAdapter(
                onClick = {
                    Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
                },
                onSwipe = { position ->
                    binding.rvProduct.suppressLayout(true)
                    for (i in 0 until binding.rvProduct.childCount) {
                        val viewHolder =
                            binding.rvProduct.getChildViewHolder(binding.rvProduct.getChildAt(i))
                        if (viewHolder is ListAdapter.ProductViewHolder) {
                            if (i != position) viewHolder.motionLayout.transitionToStart()
                        }
                    }
                },
                onComplete = {
                    binding.rvProduct.suppressLayout(false)
                }
            )

        binding.rvProduct.adapter = adapter
        adapter.differ.submitList(list)

       /* binding.rvProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                for (i in 0 until recyclerView.childCount) {
                    val viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i))
                    if (viewHolder is ListAdapter.ProductViewHolder) {
                        viewHolder.motionLayout.transitionToStart()
                    }
                }
            }
        })*/


    }
}