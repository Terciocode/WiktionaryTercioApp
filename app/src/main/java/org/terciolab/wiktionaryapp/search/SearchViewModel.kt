package org.terciolab.wiktionaryapp.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.terciolab.wiktionaryapp.api.ApiClient
import org.terciolab.wiktionaryapp.api.SearchWord


class SearchViewModel() : ViewModel() {
    private val _searchResults = MutableStateFlow<List<SearchWord>>(emptyList())
    val searchResults: StateFlow<List<SearchWord>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun searchWord(query: String) {
        if(query.length < 1){
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(300)
                val results = ApiClient.wiki.suggestWords(query)

                _searchResults.value = results.pages
            } catch (e: Exception) {
                _searchResults.value = emptyList() // Handle error
                throw e
            } finally {
                _isLoading.value = false
            }
        }

    }
}