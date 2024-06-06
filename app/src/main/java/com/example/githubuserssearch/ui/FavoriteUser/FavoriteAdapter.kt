package com.example.githubuserssearch.ui.FavoriteUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.database.Favorite
import com.example.githubuserssearch.databinding.ActivityFavoriteBinding
import com.example.githubuserssearch.databinding.ItemUserBinding

class FavoriteAdapter :RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val listFavorite = ArrayList<Favorite>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setListFavorite(users: List<Favorite>){
        listFavorite.clear()
        listFavorite.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(data : Favorite)
    }

    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder (val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(lisfav : Favorite, onItemClickListener: OnItemClickListener?){
            binding.root.setOnClickListener{
                onItemClickListener?.onItemClick(lisfav)
            }
            binding.userName.text = lisfav.login
            Glide.with(itemView).load(lisfav.avatar_url).centerCrop().into(binding.userImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorite[position], onItemClickListener)
    }

    override fun getItemCount(): Int = listFavorite.size
}