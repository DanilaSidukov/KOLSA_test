package com.diphrogram.kolsa_test.common.data

import android.content.Context
import com.diphrogram.kolsa_test.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceProvider {

    val emptyResult: String

    fun getQualityText(quality: Int, bitrate: Int): String
}

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val  context: Context
): ResourceProvider {

    override val emptyResult = context.getString(R.string.empty_result)

    override fun getQualityText(quality: Int, bitrate: Int) =
        context.getString(R.string.quality_data, quality, bitrate)
}