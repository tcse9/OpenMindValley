package com.openmindvalley.android.app.utils

import com.openmindvalley.android.app.data.remote.dto.ErrorDto

sealed class Resource<T>(val data: T? = null, val errorDto: ErrorDto? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(errorDto: ErrorDto? = null, message: String? = null, data: T? = null): Resource<T>(data, errorDto, message)
}
