package com.imageapp.model


import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ImageModel : ArrayList<ImageModel.ImageModelData>(){
    @Parcelize
    data class ImageModelData(
        @SerializedName("backupDetails")
        val backupDetails: BackupDetails,
        @SerializedName("coverageURL")
        val coverageURL: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("mediaType")
        val mediaType: Int,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("publishedBy")
        val publishedBy: String,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail,
        @SerializedName("title")
        val title: String,
        var localImage: Bitmap? = null
    ) : Parcelable {
        @Parcelize
        data class BackupDetails(
            @SerializedName("pdfLink")
            val pdfLink: String,
            @SerializedName("screenshotURL")
            val screenshotURL: String
        ) : Parcelable
    
        @Parcelize
        data class Thumbnail(
            @SerializedName("aspectRatio")
            val aspectRatio: Double,
            @SerializedName("basePath")
            val basePath: String,
            @SerializedName("domain")
            val domain: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("key")
            val key: String,
            @SerializedName("qualities")
            val qualities: List<Int>,
            @SerializedName("version")
            val version: Int
        ) : Parcelable
    }
}