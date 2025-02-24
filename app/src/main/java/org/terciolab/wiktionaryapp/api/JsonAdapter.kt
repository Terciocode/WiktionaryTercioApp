package org.terciolab.wiktionaryapp.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import java.io.IOException

class MeaningsJsonlAdapter {

    private val meaningAdapter: JsonAdapter<WordMeaning> = Moshi.Builder()
        .build()
        .adapter(WordMeaning::class.java)


    @FromJson
    fun fromJson(reader: JsonReader): List<WordMeaning> {
        val result = mutableListOf<WordMeaning>()

        reader.setLenient(true)
        while (reader.peek() != JsonReader.Token.END_DOCUMENT) {
            val meaning = meaningAdapter.fromJson(reader)
            if (meaning != null) {
                result.add(meaning)
            } else {
                reader.skipValue()
            }
        }

        return result
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: List<WordMeaning>?) {
        if (value == null) {
            writer.nullValue()
            return
        }

        val jsonlString = value.joinToString("\n") { meaning ->
            meaningAdapter.toJson(meaning)
        }
        writer.value(jsonlString)
    }
}

