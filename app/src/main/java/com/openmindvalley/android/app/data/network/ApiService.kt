package com.openmindvalley.android.app.data.network

import com.openmindvalley.android.app.data.remote.dto.MediaDataDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("raw/{mediaType}")
    suspend fun getMediaData(@Path("mediaType")mediaType: String): MediaDataDto
}