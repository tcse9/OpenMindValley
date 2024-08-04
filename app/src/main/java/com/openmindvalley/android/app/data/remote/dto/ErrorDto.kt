package com.stocky.android.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Error(
	@field:SerializedName("code")
	var code: String? = null,

	@field:SerializedName("description")
	var description: String? = null
)