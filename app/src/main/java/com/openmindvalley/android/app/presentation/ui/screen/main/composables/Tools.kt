package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openmindvalley.android.app.utils.viewutils.shimmerEffect

@Composable
fun ShortSnackbar(
    message: String,
    actionLabel: String?,
    duration: SnackbarDuration = SnackbarDuration.Short,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.showSnackbar(message, actionLabel, duration = duration)
    }

    SnackbarHost(
        hostState = snackbarHostState,
    )
}

@Composable
@Preview
fun ShimmerLoader() {
    Column {
        repeat(10) {
            Row(modifier = Modifier.horizontalScroll(state = rememberScrollState())) {
                repeat(5) {
                    LoaderThumbnail()
                }
            }
        }
    }
}

@Composable
@Preview
fun LoaderThumbnail() {
    val thumbnailWidth by remember {
        mutableStateOf(152.dp)
    }
    val thumbnailHeight by remember {
        mutableStateOf(228.dp)
    }
    val titleWidth by remember {
        mutableStateOf(100.dp)
    }

    val titleHeight by remember {
        mutableStateOf(24.dp)
    }

    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp)) {
        Box(modifier = Modifier
            .width(titleWidth)
            .height(titleHeight)
            .shimmerEffect())
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .size(width = thumbnailWidth, height = thumbnailHeight)
            .shimmerEffect())
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier
            .width(titleWidth)
            .height(titleHeight)
            .shimmerEffect())
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .width(titleWidth)
            .height(titleHeight)
            .shimmerEffect())
        Spacer(modifier = Modifier.height(8.dp))
    }
}