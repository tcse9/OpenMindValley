package com.openmindvalley.android.app.domain.use_case

import android.content.Context
import com.openmindvalley.android.app.data.remote.dto.toMediaCategories
import com.openmindvalley.android.app.data.remote.dto.toMediaCourse
import com.openmindvalley.android.app.data.remote.dto.toMediaNewEpisode
import com.openmindvalley.android.app.domain.model.Media
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.utils.Resource
import com.openmindvalley.android.app.utils.isNotNullOrEmpty
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
            if (data.data?.media.isNotNullOrEmpty()) {
                emit(Resource.Success(data.data?.media.toMediaNewEpisode()))
            } else if (data.data?.channels.isNotNullOrEmpty()){
                data.data?.channels?.forEach {
                    emit(Resource.Success(data.data.channels.toMediaCourse()))
                }
            } else if(data.data?.categories.isNotNullOrEmpty()) {
                emit(Resource.Success(data.data?.categories.toMediaCategories()))
            }

        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}