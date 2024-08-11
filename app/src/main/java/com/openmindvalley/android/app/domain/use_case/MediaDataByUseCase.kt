package com.openmindvalley.android.app.domain.use_case

import android.content.Context
import com.openmindvalley.android.app.R
import com.openmindvalley.android.app.data.remote.dto.ErrorDto
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

/**
 * This is an additional use case class from added domain layer.
 * Using this layer prevents changes done in UI layer if the actual
 * responses from data are changed due to refactoring etc.
 */
open class MediaDataByUseCase @Inject constructor(private val context: Context, private val repository: MediaRepository) {
    open operator fun invoke(mediaType: String): Flow<Resource<List<Media>>> = flow {
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
            emit(Resource.Error(errorDto = ErrorDto(code = e.code(), description = e.message())))
        } catch(e: IOException) {
            emit(Resource.Error(ErrorDto(description = context.getString(R.string.generic_msg_no_internet))))
        }
    }
}