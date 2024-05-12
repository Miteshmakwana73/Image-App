package com.imageapp.api

import com.imageapp.model.ImageModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getImageList(param: Map<String, String>): Response<List<ImageModel.ImageModelData>> {
        return apiService.getImageList(param)
    }

}