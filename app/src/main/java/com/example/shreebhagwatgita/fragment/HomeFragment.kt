package com.example.shreebhagwatgita.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shreebhagwatgita.NetworkManager
import com.example.shreebhagwatgita.R
import com.example.shreebhagwatgita.databinding.FragmentHomeBinding
import com.example.shreebhagwatgita.model.ChaptersItem
import com.example.shreebhagwatgita.adapter.AdapterChapters
import com.example.shreebhagwatgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        checkInternetConnection()
        return binding.root
    }

    private fun checkInternetConnection() {
        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.rvChapters.visibility = View.VISIBLE
                binding.shimmer.visibility = View.VISIBLE
                binding.tvConnection.visibility = View.GONE
                getAllChapters()
            } else {
                binding.rvChapters.visibility = View.GONE
                binding.shimmer.visibility = View.GONE
                binding.tvConnection.visibility = View.VISIBLE
            }
        }

    }

    private fun getAllChapters() {
        lifecycleScope.launch {
            viewModel.getAllChapters().collect { chapterList ->
                /*for (i in chapterList)
                    Log.d("TAG", "getAllChapters: $i")*/
                adapterChapters = AdapterChapters(::onChapterIVClicked)
                binding.rvChapters.adapter = adapterChapters
                adapterChapters.differ.submitList(chapterList)
                binding.shimmer.visibility = View.GONE
            }
        }
    }

    private fun onChapterIVClicked(chaptersItem: ChaptersItem) {
        //val bundle = Bundle()
        // bundle.putParcelable("verse",chaptersItem)
        val bundle = Bundle().apply {
            //putParcelable("verse", chaptersItem)
            putInt("chapterNumber", chaptersItem.chapter_number)
            putString("chapterTitle", chaptersItem.name_translated)
            putString("chapterDesc", chaptersItem.chapter_summary)
            putInt("verseCount", chaptersItem.verses_count)
        }
        findNavController().navigate(R.id.action_homeFragment_to_verseFragment, bundle)
    }

    companion object {

    }
}