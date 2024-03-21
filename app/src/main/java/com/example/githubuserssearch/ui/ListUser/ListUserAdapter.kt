package com.example.githubuserssearch.ui.ListUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.databinding.ItemUserBinding


class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<GithubResponseItem>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setListUser(users: List<GithubResponseItem>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(data : GithubResponseItem)
    }

    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(listUser: GithubResponseItem, onItemClickListener: OnItemClickListener?){

            binding.root.setOnClickListener{
                onItemClickListener?.onItemClick(listUser)
            }
            binding.userName.text = listUser.login
            Glide.with(itemView).load(listUser.avatarUrl).centerCrop().into(binding.userImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position], onItemClickListener)
    }

}