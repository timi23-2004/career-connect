package com.example.jobs

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditJobScreen(navController: NavController, viewModel: JobViewModel, jobId: Int) {
    val context = LocalContext.current
    val job = viewModel.getJobById(jobId)

    var title by remember { mutableStateOf(job?.title ?: "") }
    var company by remember { mutableStateOf(job?.company ?: "") }
    var location by remember { mutableStateOf(job?.location ?: "") }
    var description by remember { mutableStateOf(job?.description ?: "") }
    var logoUrl by remember { mutableStateOf(job?.logoUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Job") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Edit Job Listing",
                style = MaterialTheme.typography.h5.copy(fontSize = 24.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Job Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = company,
                onValueChange = { company = it },
                label = { Text("Company") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )
            OutlinedTextField(
                value = logoUrl,
                onValueChange = { logoUrl = it },
                label = { Text("Logo URL") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val updatedJob = job?.copy(
                        title = title,
                        company = company,
                        location = location,
                        description = description,
                        logoUrl = logoUrl
                    )
                    if (updatedJob != null) {
                        viewModel.updateJob(updatedJob)
                        Toast.makeText(context, "Job updated successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Update Job")
            }
        }
    }
}