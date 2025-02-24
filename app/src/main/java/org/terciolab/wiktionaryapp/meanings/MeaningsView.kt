package org.terciolab.wiktionaryapp.meanings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.terciolab.wiktionaryapp.api.WordMeaning

@Composable
fun MeaningsView(word: String, viewModel: MeaningsViewModel = viewModel()) {
    val wordMeanings by viewModel.wordMeanings.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(word) {
        viewModel.fetchWordMeanings(word)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = word,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(wordMeanings) { wordMeaning ->
                    WordMeaningItem(wordMeaning)
                }
            }
        }
    }
}

@Composable
fun WordMeaningItem(meaning: WordMeaning) {
    Column(verticalArrangement = Arrangement.Top) {

        Text(
            text = "${meaning.lang} | ${meaning.pos}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        meaning.senses.forEachIndexed { i, sense ->
            Text(
                text = "$i. " + sense.glosses.joinToString(". "),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Left
            )
            Text(
                text = sense.tags?.joinToString(", ","[","]") ?: "",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

