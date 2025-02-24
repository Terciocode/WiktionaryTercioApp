package org.terciolab.wiktionaryapp.meanings

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.terciolab.wiktionaryapp.api.ApiClient
import org.terciolab.wiktionaryapp.api.WordMeaning


class MeaningsViewModel() : ViewModel() {

    private var _wordMeanings = MutableStateFlow<List<WordMeaning>>(emptyList())
    val wordMeanings: StateFlow<List<WordMeaning>> = _wordMeanings

    private val _isLoading = MutableStateFlow(false)
    val isLoading:  StateFlow<Boolean> = _isLoading

    fun fetchWordMeanings(word: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val meanings = ApiClient.kaikki.getWordMeanings(
                    word.substring(0, 1),
                    word.substring(0, 2),
                    word
                )
                _wordMeanings.value = meanings
            } catch (e: Exception) {
                _wordMeanings.value = emptyList()
                Log.e("Error",e.toString())
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }
}