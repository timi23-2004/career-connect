package com.example.jobs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyJobListScreen(navController: NavController, viewModel: JobViewModel) {
    val myJobs = viewModel.myJobList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Job Listings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        if (myJobs.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No job listings found", style = MaterialTheme.typography.h6)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(myJobs.value) { job ->
                    JobCard(job, navController, viewModel, isMyJobList = true)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}