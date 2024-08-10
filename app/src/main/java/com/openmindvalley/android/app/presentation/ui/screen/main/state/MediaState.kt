package com.openmindvalley.android.app.presentation.ui.screen.main.state

import com.openmindvalley.android.app.data.remote.dto.ErrorDto
import com.openmindvalley.android.app.domain.model.Media

data class MediaState(
    var isLoading: Boolean = true,
    var isFailed: Boolean = false,
    var errorMessage: String = "",
    var data: List<Media>? = null,
    var error: ErrorDto? = null
)