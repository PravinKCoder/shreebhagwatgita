package com.example.shreebhagwatgita.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shreebhagwatgita.R
import com.example.shreebhagwatgita.databinding.FragmentVerseDetailBinding
import com.example.shreebhagwatgita.model.Translation
import com.example.shreebhagwatgita.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class VerseDetailFragment : Fragment() {
    private lateinit var binding: FragmentVerseDetailBinding
    private val viewModel: MainViewModel by viewModels()
    private var chapterNo = 0
    private var verseNo = 0;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerseDetailBinding.inflate(layoutInflater)

        getAndSetChapterDetails()
        getParticularVerse()

        return binding.root
    }


    private fun getAndSetChapterDetails() {
        if (arguments != null) {
            val bundle = arguments
            chapterNo = bundle?.getInt("chapterNo")!!
            verseNo = bundle?.getInt("verseNo")!!

            binding.tvVerseNumber.text = "||$chapterNo.$verseNo||"
        }
    }

    private fun getParticularVerse() {
        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNo, verseNo).collect { verse ->

                try {
                    Log.d("TAG", "getParticularVerse: $verse")
                    binding.tvVerse.text = verse.text
                    binding.tvTranslation.text = verse.transliteration
                    binding.tvWordEnglish.text = verse.transliteration
                    val englishTranslationList = arrayListOf<Translation>()
                    for (i in verse.translations) {
                        if (i.language == "english") englishTranslationList.add(i)
                    }
                    val englishTranslationListSize = englishTranslationList.size

                    if (englishTranslationList.isNotEmpty()) {
                        binding.tvAuthor.text = englishTranslationList[0].author_name
                        binding.tvText.text = englishTranslationList[0].description

                        //if (englishTranslationListSize==1) binding
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}