package com.example.jobs
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JobDetailScreen(jobId: Int, navController: NavController, viewModel: JobViewModel) {
    val job = viewModel.getJobById(jobId)
    if (job != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Job Details")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Button(
                    onClick = { navController.navigate("applyJob/${job.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Apply")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = job.title, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = job.company, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = job.location, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Job Description", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = job.description, style = MaterialTheme.typography.body1)
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Job Details")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = "Job not found", style = MaterialTheme.typography.h6)
            }
        }
    }
}