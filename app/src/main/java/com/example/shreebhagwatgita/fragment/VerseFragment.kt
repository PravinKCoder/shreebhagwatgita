package com.example.shreebhagwatgita.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shreebhagwatgita.R
import com.example.shreebhagwatgita.adapter.AdapterVerses
import com.example.shreebhagwatgita.databinding.FragmentVerseBinding
import com.example.shreebhagwatgita.model.ChaptersItem
import com.example.shreebhagwatgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class VerseFragment : Fragment() {

    private lateinit var binding: FragmentVerseBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var chaptersItem: ChaptersItem
    private lateinit var adapterVerses: AdapterVerses
    private var chapterNo = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseBinding.inflate(layoutInflater)
        binding.shimmer.visibility = View.VISIBLE
        getAndSetChapterDetails()
        getAllVerses();
        onReadMoreClicked()
        return binding.root
    }

    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvReadMore.setOnClickListener {
            if (!isExpanded) {
                binding.tvChapterDescription.maxLines = 50
                isExpanded = true
            } else {
                binding.tvChapterDescription.maxLines = 4
                isExpanded = false
            }
        }
    }

    private fun getAndSetChapterDetails() {
        if (arguments != null) {
            val bundle = arguments
            /*val bundle = arguments
            chaptersItem = bundle!!.getParcelable("verses")!!
            chaptersItem = arguments?.getParcelable<ChaptersItem>("verses")!!
            val chaptersItem: ChaptersItem? = arguments?.getParcelable("verse")*/
            chapterNo = bundle?.getInt("chapterNumber")!!
            binding.apply {
                tvChapterNumber.text = bundle.getInt("chapterNumber").toString()
                tvChapterTitle.text = bundle.getString("chapterTitle")
                tvChapterDescription.text = bundle.getString("chapterDesc")
                tvNoOfVerse.text = bundle.getInt("verseCount").toString()
            }

        }
    }

    private fun getAllVerses() {

        lifecycleScope.launch {
            viewModel.getVerses(chapterNo).collect {
                /* for (i in it) {
                     Log.d("TAG", "getAllVerses: $i")
                 }*/
                adapterVerses = AdapterVerses(::onVerseItemClicked)
                binding.rvVerse.adapter = adapterVerses
                val verseList = arrayListOf<String>()

                for (currentVerse in it) {
                    for (verses in currentVerse.translations) {
                        if (verses.language == "english") {
                            verseList.add(verses.description)
                            break
                        }
                    }
                }
                adapterVerses.differ.submitList(verseList)
                binding.shimmer.visibility = View.GONE
            }
        }

    }

    private fun onVerseItemClicked(versesItem: String, verseNo: Int) {
        val bundle = Bundle()
        bundle.putInt("chapterNo", chapterNo)
        bundle.putInt("verseNo", verseNo)
        bundle.putString("versesItem", versesItem)
        findNavController().navigate(R.id.action_verseFragment_to_verseDetailFragment, bundle)
    }

    companion object {
    }
}