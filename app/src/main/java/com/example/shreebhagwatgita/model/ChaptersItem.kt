package com.example.shreebhagwatgita.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChaptersItem(
    val chapter_number: Int,
    val chapter_summary: String,
    val id: Int,
    val name: String,
    val name_meaning: String,
    val name_translated: String,
    val name_transliterated: String,
    val verses_count: Int
) : Parcelable