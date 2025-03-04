package org.terciolab.wiktionaryapp.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    private const val WIKTIONARY_SEARCH_URL = "https://%s.wiktionary.org/w/rest.php/v1/"
    private const val KAIKII_URL = "https://kaikki.org/"

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

    private var wiki : WikiService? = null

    fun getWiki(lang: String): WikiService {
        if (wiki == null) {

            wiki = Retrofit.Builder()
                .client(client)
                .baseUrl(String.format(WIKTIONARY_SEARCH_URL,lang))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WikiService::class.java)
        }
        return wiki!!
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
