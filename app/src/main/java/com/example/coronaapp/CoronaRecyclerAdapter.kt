package com.example.coronaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coronaapp.databinding.LayoutListItemBinding
import com.example.coronaapp.network.Property

class CoronaRecyclerAdapter(private val items: List<Property>) : RecyclerView.Adapter<CoronaRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Property) {
            binding.props = item
            binding.executePendingBindings()
        }
    }
}