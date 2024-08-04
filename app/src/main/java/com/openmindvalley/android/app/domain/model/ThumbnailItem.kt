package com.openmindvalley.android.app.domain.model

data class ThumbnailItem(
    val title: String?,
    val channelTitle: String? = null,
    val thumbnailImage: String?,
    val isPortrait: Boolean? = true
)