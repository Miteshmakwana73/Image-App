package com.imageapp.api

import com.imageapp.model.ImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("content/misc/media-coverages")
    suspend fun getImageList(@QueryMap param: Map<String, String>): Response<List<ImageModel.ImageModelData>>

}

