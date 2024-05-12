package com.imageapp.repository

import com.imageapp.api.ApiHelper
import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getProductList(param: Map<String, String>) = apiHelper.getImageList(param)
}