package com.ayush.noonacademy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayush.noonacademy.R
import com.ayush.noonacademy.databinding.LayoutImdbItemBinding
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<OmdbItemViewHolder>() {
    var omdbIttemList: List<OmdbItem>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OmdbItemViewHolder {
        val binding =
            LayoutImdbItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OmdbItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return omdbIttemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: OmdbItemViewHolder, position: Int) {
        omdbIttemList?.let {
            holder.bind(it[position])
        }
    }

    fun updateList(list: List<OmdbItem>) {
        this.omdbIttemList = list
        notifyDataSetChanged()
    }

    companion object {
        /**
         *  can be used but as the list is based on the search query if the query is same then result will not change,
         *  in case of pagging it can be used
         **/
//        private val OMDB_COMPARATOR = object : DiffUtil.ItemCallback<OmdbItem>() {
//            override fun areItemsTheSame(oldItem: OmdbItem, newItem: OmdbItem): Boolean =
//                oldItem.imdbID == newItem.imdbID
//
//            override fun areContentsTheSame(oldItem: OmdbItem, newItem: OmdbItem): Boolean =
//                oldItem.title == newItem.title
//        }
    }
}

class OmdbItemViewHolder(private val binding: LayoutImdbItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(omdbItem: OmdbItem) {
        binding.tvTitle.text = omdbItem.title
        Glide.with(binding.root.context)
            .load(omdbItem.poster)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.ivIm)
        binding.tvType.text = omdbItem.type
        binding.tvYear.text = omdbItem.year
    }

}