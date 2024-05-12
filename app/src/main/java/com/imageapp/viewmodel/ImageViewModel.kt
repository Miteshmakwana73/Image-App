package com.imageapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imageapp.model.ImageModel
import com.imageapp.repository.ImageRepository
import com.imageapp.network.NetworkHelper
import com.imageapp.network.Resource
import com.imageapp.utils.Constants
import com.imageapp.utils.MyLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _imageListResponse: MutableLiveData<Resource<List<ImageModel.ImageModelData>>> =
        MutableLiveData()
    val imageListResponse: LiveData<Resource<List<ImageModel.ImageModelData>>> get() = _imageListResponse

    /**
     * Get all users post api
     */
    fun getProductList(param: Map<String, String>) =
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                try {
                    _imageListResponse.value = Resource.loading(null)
                    repository.getProductList(param)
                        .let {
                            if (it.isSuccessful) {
                                _imageListResponse.postValue(Resource.success(it.body()))
                            } else _imageListResponse.postValue(
                                Resource.error(
                                    Constants.MSG_SOMETHING_WENT_WRONG,
                                    null
                                )
                            )
                        }
                } catch (e: Exception) {
                    MyLog.e(javaClass.simpleName, e.printStackTrace().toString())
                    _imageListResponse.postValue(
                        Resource.error(
                            e.message.toString(),
//                            Constants.MSG_SOMETHING_WENT_WRONG,
                            null
                        )
                    )
                }
            } else _imageListResponse.postValue(
                Resource.error(
                    Constants.MSG_NO_INTERNET_CONNECTION,
                    null
                )
            )
        }

}