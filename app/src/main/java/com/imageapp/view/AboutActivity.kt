package com.imageapp.view

import android.os.Bundle
import com.imageapp.core.BaseActivity
import com.imageapp.databinding.ActivityAboutBinding
import com.imageapp.utils.Constants

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgClose.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tvAbout.text = Constants.ABOUT
    }
}