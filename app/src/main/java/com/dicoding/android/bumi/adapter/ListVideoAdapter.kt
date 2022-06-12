package com.dicoding.android.bumi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.android.bumi.data.model.ListVideosItem
import com.dicoding.android.bumi.databinding.ItemRowVideoBinding

class ListVideoAdapter : RecyclerView.Adapter<ListVideoAdapter.ListViewHolder>() {

    private val listVideo = ArrayList<ListVideosItem>()
    private var ItemClickCallback: OnItemClickCallback? = null

    fun SetOnItemClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.ItemClickCallback = ItemClickCallback
    }

    inner class ListViewHolder(private var binding: ItemRowVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindingView(video: ListVideosItem) {
            binding.root.setOnClickListener {
                ItemClickCallback?.ItemClicked(video)
            }

            binding.apply {
                Glide.with(itemView).load(video.thumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(itemVideo)
                itemTitle.text = StringBuilder(video.title)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(listHomeVideo: ArrayList<ListVideosItem>) {
        listVideo.clear()
        listVideo.addAll(listHomeVideo)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindingView(listVideo[position])
    }

    override fun getItemCount(): Int = listVideo.size

    interface OnItemClickCallback {
        fun ItemClicked(userData: ListVideosItem)
    }
}