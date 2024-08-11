package com.openmindvalley.android.app.repository

import com.openmindvalley.android.app.data.network.ApiService
import com.openmindvalley.android.app.data.remote.dto.Channel
import com.openmindvalley.android.app.data.remote.dto.Data
import com.openmindvalley.android.app.data.remote.dto.MediaDataDto
import com.openmindvalley.android.app.data.remote.dto.MediaItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

class MediaRepositoryImplTest {
    @Mock
    private lateinit var mockApiService: ApiService


    private lateinit var mediaRepository: MediaRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mediaRepository = MediaRepositoryImpl(mockApiService)
    }

    @Test
    fun test_getMediaData_returns_correct_data() = runTest {
        // Arrange
        val mediaType = "z5AExTtw"
        val expectedMediaData = MediaDataDto(
            data = Data(
                media = listOf(
                    MediaItem(
                        type = "type",
                        title = "Conscious Parenting",
                        channel = Channel(title = "Little Humans")
                    )
                )
            )
        )

        // Stubbing the mock to return the expected data
        whenever(mockApiService.getMediaData(mediaType)).thenReturn(expectedMediaData)

        // Act
        val actualMediaData = mediaRepository.getMediaData(mediaType)

        // Assert
        assertEquals(expectedMediaData, actualMediaData)

        // Verify that the ApiService's getMediaData method was called
        verify(mockApiService).getMediaData(mediaType)
    }

    @Test(expected = Exception::class)
    fun test_getMediaData_throws_exception_when_ApiService_fails() = runTest {
        // Given
        val mediaType = "z5AExTtw"
        whenever(mockApiService.getMediaData(mediaType)).thenThrow(Exception("API call failed"))

        // When
        mediaRepository.getMediaData(mediaType)

        // Then
        // Exception is expected to be thrown
    }
}