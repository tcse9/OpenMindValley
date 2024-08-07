package com.openmindvalley.android.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.domain.model.ThumbnailItem
import com.openmindvalley.android.app.utils.isNotNullOrEmpty

data class MediaDataDto(

	@field:SerializedName("data")
	val data: Data? = null
)

data class CoverAsset(

	@field:SerializedName("url")
	val url: String? = null
)

data class Data(

	@field:SerializedName("media")
	val media: List<MediaItem?>? = null,

	@field:SerializedName("channels")
	val channels: List<ChannelsItem?>? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null
)

data class Channel(

	@field:SerializedName("title")
	val title: String? = null
)

data class MediaItem(

	@field:SerializedName("channel")
	val channel: Channel? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("coverAsset")
	val coverAsset: CoverAsset? = null
)

data class ChannelsItem(

	@field:SerializedName("series")
	val series: List<SeriesItem?>? = null,

	@field:SerializedName("mediaCount")
	val mediaCount: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("coverAsset")
	val coverAsset: CoverAsset? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("latestMedia")
	val latestMedia: List<LatestMediaItem?>? = null,

	@field:SerializedName("iconAsset")
	val iconAsset: IconAsset? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class SeriesItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("coverAsset")
	val coverAsset: CoverAsset? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class IconAsset(

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class LatestMediaItem(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("coverAsset")
	val coverAsset: CoverAsset? = null
)

data class CategoriesItem(

	@field:SerializedName("name")
	val name: String? = null
)

fun List<MediaItem?>?.toMediaNewEpisode(): List<Media.NewEpisode> {
	val thumbnailItems = arrayListOf<ThumbnailItem>()
	this?.forEach {
		thumbnailItems.add(
			ThumbnailItem(
				title = it?.title,
				channelTitle = it?.channel?.title,
				thumbnailImage = it?.coverAsset?.url
			)
		)
	}
	return listOf(Media.NewEpisode(title = "New Spisodes", list = thumbnailItems))
}

fun List<ChannelsItem?>?.toMediaCourse(): List<Media> {
	val mediaCourses = arrayListOf<Media.Course>()
	this?.forEach { item ->
		val thumbnailItems = arrayListOf<ThumbnailItem>()
		item?.latestMedia?.forEach {
			thumbnailItems.add(
				ThumbnailItem(
					isPortrait = !item.series.isNotNullOrEmpty(),
					title = it?.title,
					channelTitle = null,
					thumbnailImage = it?.coverAsset?.url
				)
			)
		}

		mediaCourses.add(
			Media.Course(
				title = item?.title,
				iconImage = null,
				mediaCount = item?.mediaCount ?: 0,
				list = thumbnailItems,
				isMediaTypeSeries = item?.series.isNotNullOrEmpty()
			)
		)
	}
	return mediaCourses
}

fun List<CategoriesItem?>?.toMediaCategories(): List<Media.Category> {
	val mediaCategories = arrayListOf<Media.Category>()
	val names = arrayListOf<String>()
	this?.forEach {
		it?.name?.let { it1 -> names.add(it1) }
	}
	mediaCategories.add(Media.Category(categoryNames = names.toList()))
	return mediaCategories
}