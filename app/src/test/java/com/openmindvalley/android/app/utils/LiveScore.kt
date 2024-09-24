package com.openmindvalley.android.app.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.openmindvalley.android.app.utils.Util.decryptLiveScoreData
import org.json.JSONObject
import org.junit.Test


class LiveScore {

    @Test
    fun loadResponse() {
        val output = AssetsUtils.loadResponse(
            path = "response.json",
            tClass = Response::class.java
        )

        val result = output.data?.let { decryptLiveScoreData(it) }

        System.out.println("result_amir: $result")

        assert(true)
    }
}