package com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openmindvalley.android.app.domain.use_case.MediaDataByUseCase
import com.openmindvalley.android.app.presentation.ui.screen.main.state.MediaState
import com.openmindvalley.android.app.utils.NetworkUtils
import com.openmindvalley.android.app.utils.Resource
import com.openmindvalley.android.app.utils.isNotNullOrEmpty
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
    private val _mediaStateNewEpisode = MutableStateFlow(MediaState(isLoading = false))
    val mediaStateNewEpisode: StateFlow<MediaState> = _mediaStateNewEpisode

    private val _mediaStateChannel = MutableStateFlow(MediaState(isLoading = false))
    val mediaStateChannel: StateFlow<MediaState> = _mediaStateChannel

    private val _mediaStateCategories = MutableStateFlow(MediaState(isLoading = false))
    val mediaStateCategories: StateFlow<MediaState> = _mediaStateCategories

    private val _allDataLoaded = MutableStateFlow(false)
    val allDataLoaded: StateFlow<Boolean> = _allDataLoaded


    init {
        observerForAllData()
    }

    fun getMediaNewEpisode(mediaType: String) {
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
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMediaChannel(mediaType: String) {
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
                    _mediaStateChannel.value =  MediaState(data = result.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMediaCategories(mediaType: String) {
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
                    _mediaStateCategories.value = MediaState(data = result.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    // loading all the data i.e. all the api
    fun loadData() {
        getMediaNewEpisode("z5AExTtw")
        getMediaChannel("Xt12uVhM")
        getMediaCategories("A0CgArX3")
    }

    // this method is used for combining all the response status to know
    // if all the apis are done fething data
    private fun observerForAllData() {
        viewModelScope.launch {
            combine(mediaStateNewEpisode, mediaStateChannel, mediaStateCategories) { d1, d2, d3 ->
                (d1.data.isNotNullOrEmpty() || d1.error != null)
                        && (d2.data.isNotNullOrEmpty() || d2.error != null)
                        && (d3.data.isNotNullOrEmpty() || d3.error != null)
            }.collect { allDataLoaded ->
                _allDataLoaded.value = allDataLoaded
            }
        }
    }
}