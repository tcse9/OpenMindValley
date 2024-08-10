package com.openmindvalley.android.app.presentation.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.presentation.theme.OpenMindValleyTheme
import com.openmindvalley.android.app.presentation.ui.screen.main.composables.RootView
import com.openmindvalley.android.app.presentation.ui.screen.main.composables.ShortSnackbar
import com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
        setContent {
            OpenMindValleyTheme {
                Scaffold(bottomBar = {
                    if (!viewModel.networkUtils.isInternetConnected) {
                        ShortSnackbar(message = stringResource(R.string.generic_msg_no_internet_ui), null)
                    }
                }) { innerPadding ->
                    RootView(innerPadding)
                }
            }
        }
    }
}