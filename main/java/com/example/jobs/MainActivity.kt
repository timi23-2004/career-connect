package com.example.jobs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.jobs.ui.theme.JobsTheme
import androidx.compose.material.*


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val viewModel = ViewModelProvider(this)[JobViewModel::class.java]
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        Navigation(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}