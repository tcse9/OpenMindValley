package com.openmindvalley.android.app.domain.use_case

import android.content.Context
import com.openmindvalley.android.app.data.remote.dto.toMediaCategories
import com.openmindvalley.android.app.data.remote.dto.toMediaCourse
import com.openmindvalley.android.app.data.remote.dto.toMediaNewEpisode
import com.openmindvalley.android.app.data.remote.dto.toMediaSeries
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class MediaDataByUseCase @Inject constructor(private val context: Context, private val repository: MediaRepository) {
    operator fun invoke(mediaType: String): Flow<Resource<List<Media>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getMediaData(mediaType = mediaType)
            if (!data.data?.media.isNullOrEmpty()) {
                emit(Resource.Success(data.data?.media.toMediaNewEpisode()))
            } else if (!data.data?.channels.isNullOrEmpty()){
                data.data?.channels?.forEach {
                    if (it?.series.isNullOrEmpty()) {
                        emit(Resource.Success(data.data.channels.toMediaCourse()))
                    } else {
                        emit(Resource.Success(it?.series.toMediaSeries()))
                    }
                }
            } else if(!data.data?.categories.isNullOrEmpty()) {
                emit(Resource.Success(data.data?.categories.toMediaCategories()))
            }

        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}