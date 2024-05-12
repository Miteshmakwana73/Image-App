package com.imageapp.api


import com.imageapp.model.ImageModel
import retrofit2.Response

/**
 * Interface file for api calling
 */
interface ApiHelper {

    suspend fun getImageList(param: Map<String, String>): Response<List<ImageModel.ImageModelData>>
}