package com.imageapp.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.imageapp.R
import com.imageapp.databinding.RawImageListBinding
import com.imageapp.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * ImageAdapter list adapter : Show Task list.
 */
class ImageAdapter(
    private val mContext: Context, var mList: ArrayList<ImageModel.ImageModelData>,
    private val lifecycleCoroutineScope: CoroutineScope,
    private val viewHolderClicks: ViewHolderClicks,
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RawImageListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model = mList[holder.adapterPosition]

        try {
            val imageUrl =
                "${model.thumbnail.domain}/${model.thumbnail.basePath}/0/${model.thumbnail.key}"
            holder.apply {

                // Check if image is already loaded and cached
                if (model.localImage != null) {
                    viewBinding.imgItem.setImageBitmap(model.localImage)
                    return
                }

                model.job = launchJob(model, imageUrl)

            }

            holder.itemView.setOnClickListener {
                viewHolderClicks.onClickItem(model, holder.adapterPosition)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImage(url: String, imgItem: ImageView): Bitmap? {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            val bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            inputStream.close()
            connection.disconnect()
            return bitmap
        } catch (e: IOException) {
            imgItem.setImageResource(R.drawable.ic_placeholder_image)
            e.printStackTrace()
        }
        return null
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(val viewBinding: RawImageListBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun launchJob(model: ImageModel.ImageModelData, imageUrl: String): Job =
            lifecycleCoroutineScope.launch(Dispatchers.IO) {
                val bitmap = loadImage(imageUrl, viewBinding.imgItem)
                bitmap?.let {
                    // Cache loaded image
                    model.localImage = it

                    // Update UI on main thread
                    withContext(Dispatchers.Main) {
                        // Check if ViewHolder is still bound to the same URL
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            viewBinding.imgItem.setImageBitmap(it)
                        }
                    }
                }
            }
    }

    interface ViewHolderClicks {
        fun onClickItem(model: ImageModel.ImageModelData, position: Int)
    }
}