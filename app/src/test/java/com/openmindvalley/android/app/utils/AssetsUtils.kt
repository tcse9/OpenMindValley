package com.openmindvalley.android.app.utils

import com.google.gson.GsonBuilder
import java.io.File


class AssetsUtils {

    companion object {

        fun <T> loadResponse(
            path: String,
            tClass: Class<T>?,
            isPrettyPrinting: Boolean? = true,
            isUnitTest: Boolean? = true
        ): T {

            val json = File(
                "src/${if (isUnitTest == true) {
                    "test"
                } else {
                    "androidTest"
                }}/assets/${path}"
            ).bufferedReader().use {
                it.readText()
            }

            val gson = GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create()

            val model = gson.fromJson(json, tClass)

            if (isPrettyPrinting == true) {
                System.out.println("Load com.openmindvalley.android.app.utils.Response ------------------------------------------------------------------------------------------------")
                System.out.println()
                System.out.println("com.openmindvalley.android.app.utils.Response file: $path")
                System.out.println(gson.toJson(model))
                System.out.println()
                System.out.println("Model: $model")
                System.out.println()
                System.out.println("--------------------------------------------------------------------------------------------------------------")
            }

            return model
        }
    }
}