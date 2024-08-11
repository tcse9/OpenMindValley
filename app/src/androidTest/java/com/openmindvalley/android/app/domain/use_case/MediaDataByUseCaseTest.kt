package com.openmindvalley.android.app.domain.use_case

import android.content.Context
import com.openmindvalley.android.app.data.remote.dto.CategoriesItem
import com.openmindvalley.android.app.data.remote.dto.Channel
import com.openmindvalley.android.app.data.remote.dto.ChannelsItem
import com.openmindvalley.android.app.data.remote.dto.CoverAsset
import com.openmindvalley.android.app.data.remote.dto.Data
import com.openmindvalley.android.app.data.remote.dto.LatestMediaItem
import com.openmindvalley.android.app.data.remote.dto.MediaDataDto
import com.openmindvalley.android.app.data.remote.dto.MediaItem
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class MediaDataByUseCaseTest {
    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var repository: MediaRepository
    private lateinit var mediaDataByUseCase: MediaDataByUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mediaDataByUseCase = MediaDataByUseCase(context, repository)
    }

    @Test
    fun invoke_should_emit_Success_when_media_data_is_available() = runTest {
        // Arrange
        val mediaType = "z5AExTtw"
        val mediaList = listOf(
            MediaItem(
                title = "Conscious Parenting",
                channel = Channel("Little Humans"),
                coverAsset = CoverAsset("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
            )
        )
        val data = MediaDataDto(data = Data(media = mediaList))
        whenever(repository.getMediaData(mediaType)).thenReturn(Resource.Success(data).data)

        // Act
        val result = mediaDataByUseCase(mediaType).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals("Conscious Parenting", (result[1] as Resource.Success).data?.first()?.list?.first()?.title)
    }

    @Test
    fun invoke_should_emit_Success_when_channels_data_is_available() = runTest {
        // Arrange
        val mediaType = "Xt12uVhM"
        val channelsList = listOf(
            ChannelsItem(
                title = "Mindvalley Mentoring",
                mediaCount = 98,
                latestMedia = listOf(LatestMediaItem(type = "course", title = "How Journaling Helped Create a \$500M Company", coverAsset = CoverAsset("https://assets.mindvalley.com/api/v1/assets/a90653b8-8475-41a9-925a-3a1bf0e7cd5f.jpg?transform=w_1080")))
            )
        )
        val data = MediaDataDto(data = Data(channels = channelsList))
        whenever(repository.getMediaData(mediaType)).thenReturn(data)

        // Act
        val result = mediaDataByUseCase(mediaType).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals("Mindvalley Mentoring", (result[1] as Resource.Success).data?.first()?.title)
    }

    @Test
    fun invoke_should_emit_Success_when_categories_data_is_available() = runTest {
        // Arrange
        val mediaType = "A0CgArX3"
        val categoriesList = listOf(
            CategoriesItem(name = "Career")
        )
        val data = MediaDataDto(data = Data(categories = categoriesList))
        whenever(repository.getMediaData(mediaType)).thenReturn(data)

        // Act
        val result = mediaDataByUseCase(mediaType).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals("Career", (result[1] as Resource.Success).data?.first()?.categoryNames?.first())
    }

    @Test
    fun invoke_should_emit_Error_on_HttpException() = runTest {
        // Arrange
        val mediaType = "Xt12uVhM"
        val httpException = Mockito.mock(HttpException::class.java)
        whenever(httpException.code()).thenReturn(500)
        whenever(httpException.message()).thenReturn("Internal Server Error")
        whenever(repository.getMediaData(mediaType)).thenThrow(httpException)

        // Act
        val result = mediaDataByUseCase(mediaType).toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Internal Server Error", (result[1] as Resource.Error).errorDto?.description)
    }
}