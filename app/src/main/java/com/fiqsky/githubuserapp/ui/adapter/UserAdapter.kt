package com.fiqsky.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.utils.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(

    private val list: MutableList<User>? = mutableListOf(),
    private val onClick: ((User) -> Unit)? = null,
    private val onLongClick: ((User, Int) -> Unit)? = null
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var listFav = ArrayList<User>()
        set(listFav){
            if (listFav.size>0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    /*fun addAll() {
        set(listFav){
            if (listFav.size>0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)
            notifyDataSetChanged()
        }
        *//*if (result != null) {
            list?.clear()
            list?.addAll(result)
            notifyDataSetChanged()
        }*//*
    }*/
    fun addAll(result: List<User>?) {
        if (result != null) {
            list?.clear()
            list?.addAll(result)
            notifyDataSetChanged()
        }
    }

    fun removeItem(position: Int) {
        this.list?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.list?.size ?: 0)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User?) {
            Picasso.get()
                .load(user?.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.color.design_default_color_error)
                .into(itemView.iv_avatar)
            itemView.tv_username.text = user?.userName
            itemView.setOnClickListener {
                if (user != null) {
                    onClick?.invoke(user)
                }
            }
            itemView.setOnLongClickListener {
                if (user != null) {
                    onLongClick?.invoke(user, adapterPosition)
                }
                return@setOnLongClickListener true
            }
        }
    }
}