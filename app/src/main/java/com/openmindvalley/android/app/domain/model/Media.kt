package com.openmindvalley.android.app.domain.model

sealed class Media {
    open val title: String? = null
    open val iconImage: String? = null
    open val mediaCount: Int = 0
    open val list: List<ThumbnailItem>? = null
    open val categoryNames: List<String>? = null
    open val isMediaTypeSeries: Boolean = false

    data class NewEpisode(override val list: List<ThumbnailItem>?) : Media()

    data class Course(
        override val title: String?,
        override val iconImage: String?,
        override val mediaCount: Int = 0,
        override val list: List<ThumbnailItem>?,
        override val isMediaTypeSeries: Boolean

    ) : Media()

    data class Category(override val categoryNames: List<String>?) : Media()
}