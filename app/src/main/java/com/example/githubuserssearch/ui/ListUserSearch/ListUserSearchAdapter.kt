package com.example.githubuserssearch.ui.ListUserSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserssearch.data.response.ItemsItem
import com.example.githubuserssearch.databinding.ItemUserBinding

class ListUserSearchAdapter : RecyclerView.Adapter<ListUserSearchAdapter.ViewHolder>(){

    private var listUserSearch = ArrayList<ItemsItem>()
    private var onItemClickCallback: OnItemClickCallback? = null



    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUserSearch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUserSearch[position], onItemClickCallback)
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(preview: ItemsItem, onItemClickCallback: OnItemClickCallback?) {
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(preview)
            }
            binding.userName.text = "${preview.login}"
            Glide.with(binding.userImage.context).load(preview.avatarUrl.toString())
                .into(binding.userImage)
        }
    }
}