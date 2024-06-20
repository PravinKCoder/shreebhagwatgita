package com.example.shreebhagwatgita.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shreebhagwatgita.databinding.ItemViewChapterBinding
import com.example.shreebhagwatgita.model.ChaptersItem

class AdapterChapters(val onChapterIVClicked: (ChaptersItem) -> Unit) : RecyclerView.Adapter<AdapterChapters.ChaptersViewModel>() {
    class ChaptersViewModel(val binding: ItemViewChapterBinding) : ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ChaptersItem>() {
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewModel {
        return ChaptersViewModel(
            ItemViewChapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ChaptersViewModel, position: Int) {
        val chapter = differ.currentList[position]
        holder.binding.apply {
            tvChapterNumber.text = "Chapter ${chapter.chapter_number}"
            tvChapterTitle.text = chapter.name_translated
            tvChapterDescription.text = chapter.chapter_summary
            tvVerseCount.text = chapter.verses_count.toString()
        }

        holder.binding.ll.setOnClickListener(View.OnClickListener {
            onChapterIVClicked(chapter)
        })
    }
}