package com.openmindvalley.android.app.repository

import com.openmindvalley.android.app.data.remote.dto.MediaDataDto
import com.openmindvalley.android.app.data.network.ApiService
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(private val api: ApiService) : MediaRepository {
    override suspend fun getMediaData(mediaType: String): MediaDataDto {
        return api.getMediaData(mediaType)
    }
}