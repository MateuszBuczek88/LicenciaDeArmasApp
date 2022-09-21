package com.example.licenciadearmas.data

import android.content.res.AssetManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter


class FromFileDataSource(val filesIds: Map<Section, String>, val assetManager: AssetManager) :
    ILocalDataSource {
    override suspend fun getQuestionList(section: Section): Result<List<Question>> {

        return readFile(section)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun readFile(section: Section): Result<List<Question>> {
        val json = filesIds.get(section)?.let {
            assetManager.open(it).bufferedReader().use {
                it.readText()
            }
        }
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<List<Question>> = moshi.adapter()
        return kotlin.runCatching { jsonAdapter.fromJson(json) ?: throw Exception("null Json") }
    }
}
