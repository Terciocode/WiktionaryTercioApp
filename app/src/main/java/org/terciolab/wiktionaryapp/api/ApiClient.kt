package org.terciolab.wiktionaryapp.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    private const val WIKTIONARY_SEARCH_URL = "https://en.wiktionary.org/w/rest.php/v1/"
    private const val KAIKII_URL = "https://kaikki.org/dictionary/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(MeaningsJsonlAdapter())
        .build()


    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    val wiki : WikiService by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(WIKTIONARY_SEARCH_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WikiService::class.java)
    }

    val kaikki : KaikkiService by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(KAIKII_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(KaikkiService::class.java)
    }

}
