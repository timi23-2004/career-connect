package com.example.jobs

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JobListScreen(navController: NavController, viewModel: JobViewModel) {
    val jobs = viewModel.jobList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Listings") },
                actions = {
                    IconButton(onClick = { navController.navigate("createJob") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Job")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(jobs.value) { job ->
                JobCard(job, navController, viewModel)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun JobCard(
    job: Job,
    navController: NavController,
    viewModel: JobViewModel,
    isMyJobList: Boolean = false,
    onDelete: (() -> Unit)? = null
) {
    val isFavorite = remember { mutableStateOf(viewModel.favoriteJobs.value.contains(job)) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
    ) {
        Box {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("jobDetail/${job.id}") },
                elevation = 8.dp
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = job.logoUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = job.title,
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                        Text(
                            text = job.company,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.secondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = job.location,
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                    }
                    if (isMyJobList) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.navigate("editJob/${job.id}")
                                },
                            tint = Color.Blue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    viewModel.removeMyJob(job)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Job removed from your list")
                                    }
                                },
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    isFavorite.value = !isFavorite.value
                                    if (isFavorite.value) {
                                        viewModel.addFavoriteJob(job)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Added to favorite list")
                                        }
                                    } else {
                                        visible = false
                                        onDelete?.invoke()
                                        viewModel.removeFavoriteJob(job)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Removed from favorite list")
                                        }
                                    }
                                },
                            tint = if (isFavorite.value) Color.Red else Color.Gray
                        )
                    }
                    Button(
                        onClick = { navController.navigate("jobDetail/${job.id}") },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    ) {
                        Text(text = "Description")
                    }
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
