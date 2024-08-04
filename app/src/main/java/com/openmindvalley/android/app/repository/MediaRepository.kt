package com.openmindvalley.android.app.repository

import com.openmindvalley.android.app.data.remote.dto.MediaDataDto

interface MediaRepository {
    suspend fun getMediaData(mediaType: String): MediaDataDto
}