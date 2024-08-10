package com.openmindvalley.android.app.presentation.ui.screen.main.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel.MainViewModel

@Composable
fun RootView(paddingValues: PaddingValues, viewModel: MainViewModel = hiltViewModel()) {
    var allDataHasLoaded by remember { mutableStateOf(false) }
    viewModel.allDataLoaded.asLiveData().observe(LocalLifecycleOwner.current) {
        allDataHasLoaded = it
    }

    if (!allDataHasLoaded) {
        ShimmerLoader()
    } else {
        Channel(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
    }
}