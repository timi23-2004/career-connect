package com.example.jobs

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun showSnackbar(scope: CoroutineScope, snackbarHostState: SnackbarHostState, message: String) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
    }
}