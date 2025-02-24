package org.terciolab.wiktionaryapp.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.terciolab.wiktionaryapp.api.SearchWord

@Composable
fun SearchView(navController: NavController, viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(10.dp))
    {
        SearchBar(query, onQueryChange = {
            query = it
            viewModel.searchWord(it)
        })

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            WordList(searchResults, navController)
        }
    }
}

@Composable
fun SearchBar(query : String, onQueryChange: (String) -> Unit){
    Text(
        text = "Wiktionary search",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search for words...") }
    )
}


@Composable
fun WordList(words: List<SearchWord>,navController: NavController) {
    LazyColumn {
        items(words) { word ->
            WordItem(word, {
                navController.navigate("details/${word.title}")
            })
        }
    }
}

@Composable
fun WordItem(word: SearchWord, onClickWord: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ).clickable {
                onClickWord()
            }
    ) {
        Text(
            text = word.title,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
