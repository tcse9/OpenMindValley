package com.openmindvalley.android.app.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openmindvalley.android.app.domain.use_case.MediaDataByUseCase
import com.openmindvalley.android.app.presentation.state.MediaState
import com.openmindvalley.android.app.utils.NetworkUtils
import com.openmindvalley.android.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mediaDataByUseCase: MediaDataByUseCase, val networkUtils: NetworkUtils) : ViewModel() {
    private val _mediaStateNewEpisode = mutableStateOf(MediaState(isLoading = false))
    val mediaStateNewEpisode: State<MediaState> = _mediaStateNewEpisode

    private val _mediaStateChannel = mutableStateOf(MediaState(isLoading = false))
    val mediaStateChannel: State<MediaState> = _mediaStateChannel

    private val _mediaStateCategories = mutableStateOf(MediaState(isLoading = false))
    val mediaStateCategories: State<MediaState> = _mediaStateCategories

    private fun getMediaNewEpisode(mediaType: String) {
        mediaDataByUseCase(mediaType).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _mediaStateNewEpisode.value = MediaState(isLoading = true)
                }

                is Resource.Error -> _mediaStateNewEpisode.value = MediaState(
                    isLoading = false,
                    data = result.data,
                    errorMessage = result.message ?: "Error loading data",
                    error = result.errorDto
                )

                is Resource.Success -> _mediaStateNewEpisode.value =
                    MediaState(data = result.data, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    private fun getMediaChannel(mediaType: String) {
        mediaDataByUseCase(mediaType).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _mediaStateChannel.value = MediaState(isLoading = true)
                }

                is Resource.Error -> _mediaStateChannel.value = MediaState(
                    isLoading = false,
                    data = result.data,
                    errorMessage = result.message ?: "Error loading data"
                )

                is Resource.Success -> _mediaStateChannel.value =
                    MediaState(data = result.data, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    private fun getMediaCategories(mediaType: String) {
        mediaDataByUseCase(mediaType).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _mediaStateCategories.value = MediaState(isLoading = true)
                }

                is Resource.Error -> _mediaStateCategories.value = MediaState(
                    isLoading = false,
                    data = result.data,
                    errorMessage = result.message ?: "Error loading data"
                )

                is Resource.Success -> _mediaStateCategories.value =
                    MediaState(data = result.data, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    fun loadData() {
        getMediaNewEpisode("z5AExTtw")
        getMediaChannel("Xt12uVhM")
        getMediaCategories("A0CgArX3")
    }
}