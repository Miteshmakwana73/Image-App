package com.imageapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnScrollChangeListener
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imageapp.R
import com.imageapp.core.BaseActivity
import com.imageapp.databinding.ActivityImageListingBinding
import com.imageapp.model.ImageModel
import com.imageapp.network.Status
import com.imageapp.utils.makeGone
import com.imageapp.utils.makeVisible
import com.imageapp.utils.showSnackBarToast
import com.imageapp.view.adapter.ImageAdapter
import com.imageapp.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@AndroidEntryPoint
class ImageListingActivity : BaseActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var binding: ActivityImageListingBinding

    private var mList: ArrayList<ImageModel.ImageModelData> = arrayListOf()

    private val taskViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        setObserver()
        with(binding.rvImages) {
            val mGridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = mGridLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = ImageAdapter(
                context, mList, coroutineScope,
                object : ImageAdapter.ViewHolderClicks {
                    override fun onClickItem(
                        model: ImageModel.ImageModelData,
                        position: Int
                    ) {
                        //click of recyclerview item
                    }
                }
            )

            /*addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                        val first = mGridLayoutManager.findFirstVisibleItemPosition()
                        val last = mGridLayoutManager.findLastVisibleItemPosition()
                        Log.e(TAG, "onScrolled: $first and $last")
                        for (i in 0..first) {
                            if (mList[i].job?.isActive == true) {
                                mList[i].job?.cancel()
                                mList[i].getImage = false
                            }
                        }

//                        for (i in last..<mList.size) {
//                            if (mList[i].job?.isActive == true) {
//                                mList[i].job?.cancel()
//                                mList[i].getImage = false
//                            }
//                        }

                        for (i in first..last) {
                            mList[i].getImage = true
                            if(mList[i].job?.isCompleted == false) {
                                binding.rvImages.adapter?.notifyItemChanged(i)
                            }
                        }

                }

            })*/
        }

        binding.imgMore.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.title) {
                    getString(R.string.about) -> {
                        startActivity(Intent(this, AboutActivity::class.java))
                    }
                }
                true
            }
            popupMenu.show()
        }
        startFromFresh()

    }

    private fun startFromFresh() {
        val param: Map<String, String> = mapOf("limit" to "100")
        taskViewModel.getProductList(param)
    }

    /**
     * set observer
     */
    private fun setObserver() {
        taskViewModel.imageListResponse.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress(this)
                }

                Status.SUCCESS -> {
                    hideProgress()
                    it.data?.let { result ->
                        mList.clear()
                        mList.addAll(result)
                        binding.rvImages.adapter?.notifyDataSetChanged()
                        manageData()
                    }
                }

                Status.ERROR -> {
                    hideProgress()
                    manageData()
                    it.message?.let { it1 -> binding.root.showSnackBarToast(it1) }
                }
            }
        }

    }

    /**
     * Manage screen on empty list
     */
    private fun manageData() {
        if (mList.isEmpty()) {
            binding.rvImages.makeGone()
            binding.tvNoDataFound.makeVisible()
        } else {
            binding.rvImages.makeVisible()
            binding.tvNoDataFound.makeGone()
        }
    }

    /**
     * Manage coroutineScope
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}