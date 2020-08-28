package com.example.network.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.network.R
import com.example.network.data.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    var models: List<Post> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun populateModel(model: Post){
            itemView.tvTheme.text = model.theme
            itemView.tvUsername.text = model.username
            itemView.tvPostText.text = model.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int =models.size
}