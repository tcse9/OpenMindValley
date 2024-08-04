package com.openmindvalley.android.app.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.domain.use_case.MediaDataByUseCase
import com.openmindvalley.android.app.presentation.state.MediaState
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mediaDataByUseCase: MediaDataByUseCase) : ViewModel() {
    private val _mediaState = mutableStateOf(MediaState(isLoading = false))
    val mediaState: State<MediaState> = _mediaState

    fun getMediaData(mediaType: String) {
        mediaDataByUseCase(mediaType).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _mediaState.value = MediaState(isLoading = true)
                }

                is Resource.Error -> _mediaState.value = MediaState(
                    isLoading = false,
                    data = result.data,
                    errorMessage = result.message ?: "Error loading data"
                )

                is Resource.Success -> _mediaState.value =
                    MediaState(data = result.data, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }
}