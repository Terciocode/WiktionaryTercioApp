package org.terciolab.wiktionaryapp.api

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

@JsonClass(generateAdapter = true)
data class SearchWord(
    val title: String,
    val key: String
)

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val pages: List<SearchWord>
)

@JsonClass(generateAdapter = true)
data class Sense(
    val glosses: List<String>,
    val tags: List<String>?
)

@JsonClass(generateAdapter = true)
data class WordMeaning(
    val senses: List<Sense>,
    val pos: String,
    val word: String,
    val lang: String
)

interface WikiService {
    @GET("search/title")
    suspend fun suggestWords(@Query("q") query: String, @Query("limit") limit: Int = 10): SearchResponse
}

interface KaikkiService {
    @Streaming
    @GET("All%20languages%20combined/meaning/{firstOne}/{firstTwo}/{whole}.jsonl")
    suspend fun getWordMeanings(@Path("firstOne") firstOne: String, @Path("firstTwo") firstTwo: String, @Path("whole") whole: String): List<WordMeaning>
}

