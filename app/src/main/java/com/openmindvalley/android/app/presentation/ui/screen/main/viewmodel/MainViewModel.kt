package com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openmindvalley.android.app.domain.use_case.MediaDataByUseCase
import com.openmindvalley.android.app.presentation.ui.screen.main.state.MediaState
import com.openmindvalley.android.app.utils.NetworkUtils
import com.openmindvalley.android.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mediaDataByUseCase: MediaDataByUseCase, val networkUtils: NetworkUtils) : ViewModel() {
    private val _mediaStateNewEpisode = mutableStateOf(MediaState(isLoading = false))
    val mediaStateNewEpisode: State<MediaState> = _mediaStateNewEpisode

    private val _mediaStateChannel = mutableStateOf(MediaState(isLoading = false))
    val mediaStateChannel: State<MediaState> = _mediaStateChannel

    private val _mediaStateCategories = mutableStateOf(MediaState(isLoading = false))
    val mediaStateCategories: State<MediaState> = _mediaStateCategories

    private val _data1 = MutableStateFlow<MediaState?>(null)
    val data1: StateFlow<MediaState?> = _data1

    private val _data2 = MutableStateFlow<MediaState?>(null)
    val data2: StateFlow<MediaState?> = _data2

    private val _data3 = MutableStateFlow<MediaState?>(null)
    val data3: StateFlow<MediaState?> = _data3

    private val _allDataLoaded = MutableStateFlow(false)
    val allDataLoaded: StateFlow<Boolean> = _allDataLoaded


    init {
        observerForAllData()
    }

    private fun getMediaNewEpisode(mediaType: String) {
        mediaDataByUseCase(mediaType).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _mediaStateNewEpisode.value = MediaState(isLoading = true)
                }

                is Resource.Error -> _mediaStateNewEpisode.value = MediaState(
                    isLoading = false,
                    data = null,
                    error = result.errorDto,
                    errorMessage = result.message ?: "",
                )

                is Resource.Success -> {
                    _mediaStateNewEpisode.value = MediaState(data = result.data, isLoading = false)
                    _data1.value = _mediaStateNewEpisode.value
                }
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
                    data = null,
                    error = result.errorDto,
                    errorMessage = result.message ?: "",
                )

                is Resource.Success -> {
                    _mediaStateChannel.value =
                    MediaState(data = result.data, isLoading = false)
                    _data2.value = _mediaStateChannel.value
                }
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
                    data = null,
                    error = result.errorDto,
                    errorMessage = result.message ?: "",
                )

                is Resource.Success -> {
                    _mediaStateCategories.value =
                        MediaState(data = result.data, isLoading = false)
                    _data3.value = _mediaStateCategories.value
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadData() {
        getMediaNewEpisode("z5AExTtw")
        getMediaChannel("Xt12uVhM")
        getMediaCategories("A0CgArX3")
    }

    private fun observerForAllData() {
        viewModelScope.launch {
            combine(data1, data2, data3) { d1, d2, d3 ->
                d1 != null && d2 != null && d3 != null
            }.collect { allDataLoaded ->
                _allDataLoaded.value = allDataLoaded
            }
        }
    }
}