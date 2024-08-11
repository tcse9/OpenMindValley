package com.openmindvalley.android.app.data.remote.dto

import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class MediaDataDtoExtensionFunctionsTest {
    @Test
    fun toMediaNewEpisode_should_convert_MediaItem_list_to_MediaNewEpisode_list() {
        // Arrange
        val mediaItems = listOf(
            MediaItem(
                title = "Conscious Parenting",
                channel = Channel("Conscious Parenting"),
                coverAsset = CoverAsset("https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080")
            )
        )

        // Act
        val result = mediaItems.toMediaNewEpisode()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Conscious Parenting", result.first().list?.first()?.title)
    }

    @Test
    fun toMediaCourse_should_convert_ChannelsItem_list_to_MediaCourse_list() {
        // Arrange
        val channelsItems = listOf(
            ChannelsItem(
                title = "Mindvalley Mentoring",
                mediaCount = 98,
                latestMedia = listOf(LatestMediaItem(title = "How Journaling Helped Create a \$500M Company", coverAsset = CoverAsset("url1")))
            )
        )

        // Act
        val result = channelsItems.toMediaCourse()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Mindvalley Mentoring", result.first().title)
        assertEquals(98, result.first().mediaCount)
    }

    @Test
    fun toMediaCategories_should_convert_CategoriesItem_list_to_MediaCategory_list() {
        // Arrange
        val categoriesItems = listOf(
            CategoriesItem(name = "Career")
        )

        // Act
        val result = categoriesItems.toMediaCategories()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Career", result.first().categoryNames?.first())
    }
}