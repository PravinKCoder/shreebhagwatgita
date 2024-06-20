package com.example.shreebhagwatgita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shreebhagwatgita.databinding.ItemViewVersesBinding
import kotlin.reflect.KFunction2

class AdapterVerses(val onVerseItemClicked: KFunction2<String, Int, Unit>) :
    RecyclerView.Adapter<AdapterVerses.VerseViewHolder>() {


    class VerseViewHolder(val binding: ItemViewVersesBinding) : ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterVerses.VerseViewHolder {
        return VerseViewHolder(
            ItemViewVersesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterVerses.VerseViewHolder, position: Int) {
        val verse = differ.currentList[position]
        holder.binding.apply {
            tvVerseNumber.text = "Verse ${position + 1}"
            tvVerse.text = verse
        }

        holder.binding.ll.setOnClickListener {
            onVerseItemClicked(verse, position + 1)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}