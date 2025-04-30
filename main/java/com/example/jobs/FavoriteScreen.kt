package com.example.jobs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteJobListScreen(navController: NavController, viewModel: JobViewModel) {
    val favoriteJobs = viewModel.favoriteJobs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Job Listings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        if (favoriteJobs.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No favorite job listings found", style = MaterialTheme.typography.h6)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                itemsIndexed(favoriteJobs.value) { index, job ->
                    JobCard(job, navController, viewModel, onDelete = {
                        viewModel.removeFavoriteJob(job)
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}