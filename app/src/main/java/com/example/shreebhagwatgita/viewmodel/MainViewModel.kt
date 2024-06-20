package com.example.shreebhagwatgita.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shreebhagwatgita.model.ChaptersItem
import com.example.shreebhagwatgita.model.VersesItem
import com.example.shreebhagwatgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    private val appRepository = AppRepository()
    fun getAllChapters(): Flow<List<ChaptersItem>> = appRepository.getAppChapters()

    fun getVerses(chapterNo: Int): Flow<List<VersesItem>> = appRepository.getVerses(chapterNo)

    fun getParticularVerse(chapterNo: Int, verseNo: Int): Flow<VersesItem> =
        appRepository.getParticularVerse(chapterNo, verseNo)
}