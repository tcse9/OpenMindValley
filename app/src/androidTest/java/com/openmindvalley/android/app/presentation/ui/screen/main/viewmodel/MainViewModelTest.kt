package com.openmindvalley.android.app.presentation.ui.screen.main.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openmindvalley.android.app.data.network.ApiService
import com.openmindvalley.android.app.data.remote.dto.CategoriesItem
import com.openmindvalley.android.app.data.remote.dto.Channel
import com.openmindvalley.android.app.data.remote.dto.ChannelsItem
import com.openmindvalley.android.app.data.remote.dto.CoverAsset
import com.openmindvalley.android.app.data.remote.dto.MediaItem
import com.openmindvalley.android.app.data.remote.dto.toMediaCategories
import com.openmindvalley.android.app.data.remote.dto.toMediaCourse
import com.openmindvalley.android.app.data.remote.dto.toMediaNewEpisode
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.domain.use_case.MediaDataByUseCase
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.utils.NetworkUtils
import com.openmindvalley.android.app.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var mediaDataByUseCase: MediaDataByUseCase
    private lateinit var networkUtils: NetworkUtils
    private lateinit var mainViewModel: MainViewModel
    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var repository: MediaRepository
    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        networkUtils = NetworkUtils(context)
        mainViewModel = MainViewModel(mediaDataByUseCase, networkUtils)
    }

    @Test
    fun loadData_should_update_mediaStateNewEpisode() = runTest {
        // Given
        val mediaItems = listOf(
            MediaItem(
                title = "Conscious Parenting",
                channel = Channel("Conscious Parenting"),
                coverAsset = CoverAsset("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
            )
        )

        val expectedMediaData = mediaItems.toMediaNewEpisode() as List<Media>
        val resourceFlow = flowOf(Resource.Success(expectedMediaData))
        whenever(mediaDataByUseCase("z5AExTtw")).thenReturn(resourceFlow)
        // When
        mainViewModel.getMediaNewEpisode("z5AExTtw")
        delay(5000)
        // Then
        assertEquals(expectedMediaData, mainViewModel.mediaStateNewEpisode.value.data)
    }

    @Test
    fun loadData_should_update_mediaStateChannel() = runTest {
        // Given
        val channelItems = listOf(
            ChannelsItem(title = "Mindvalley Mentoring", mediaCount = 98)
        )
        val expectedMediaData = channelItems.toMediaCourse()
        val resourceFlow = flowOf(Resource.Success(expectedMediaData))
        whenever(mediaDataByUseCase("Xt12uVhM")).thenReturn(resourceFlow)
        // When
        mainViewModel.getMediaChannel("Xt12uVhM")
        delay(5000)
        // Then
        assertEquals(expectedMediaData, mainViewModel.mediaStateChannel.value.data)
    }

    @Test
    fun loadData_should_update_mediaStateCategoryl() = runTest {
        // Given
        val categoryItems = listOf(
            CategoriesItem(name = "Career")
        )
        val expectedMediaData = categoryItems.toMediaCategories() as List<Media>
        val resourceFlow = flowOf(Resource.Success(expectedMediaData))
        whenever(mediaDataByUseCase("A0CgArX3")).thenReturn(resourceFlow)
        // When
        mainViewModel.getMediaCategories("A0CgArX3")
        delay(5000)
        // Then
        assertEquals(expectedMediaData, mainViewModel.mediaStateCategories.value.data)
    }
}