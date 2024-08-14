package com.example.interntest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.interntest.R
import com.example.interntest.databinding.ItemListBinding
import com.example.interntest.local.entity.Users

class ListUserAdapter(
    private val onItemClick: (Users) -> Unit
) : PagingDataAdapter<Users, ListUserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(holder.itemView.context, user, onItemClick)
        }
    }

    inner class UserViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, user: Users, onItemClick: (Users) -> Unit) {
            binding.tvFirstName.text = user.firstname
            binding.tvLastName.text = user.lastname
            binding.tvItemEmail.text = user.email
            Glide.with(context)
                .load(user.avatar)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Users>() {
            override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
                return oldItem == newItem
            }
        }
    }
}
