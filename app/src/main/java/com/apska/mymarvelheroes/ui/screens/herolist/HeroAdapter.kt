package com.apska.mymarvelheroes.ui.screens.herolist

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apska.mymarvelheroes.R
import com.apska.mymarvelheroes.data.model.Hero
import com.apska.mymarvelheroes.databinding.HeroViewItemBinding
import com.apska.mymarvelheroes.utils.Common
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class HeroAdapter : ListAdapter<Hero, HeroAdapter.HeroViewHolder>(DiffCallBack) {

    private val TAG = this.javaClass.simpleName

    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnItemClickListener {
        fun onClick(hero: Hero)
    }

    lateinit var onReachEndListener: OnReachEndListener
    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(HeroViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = getItem(position)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onItemClickListener.onClick(hero)
            }
        })

        holder.bind(hero)

        Log.d(TAG, "onBindViewHolder: position = $position, itemCount = ${this.itemCount}")

        if (position >= 20 && position == this.itemCount - 6) {

            Log.d(TAG, "onBindViewHolder: call onReachEnd")

            onReachEndListener.onReachEnd()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitMyList(list: List<Hero>) {
        this.submitList(list)
        this.notifyDataSetChanged()
    }

    class HeroViewHolder(private var binding: HeroViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            binding.hero = hero

            Common.loadImageToView(binding.heroImage, hero.thumbnail.imageUrl)

            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id == newItem.id
        }
    }
}