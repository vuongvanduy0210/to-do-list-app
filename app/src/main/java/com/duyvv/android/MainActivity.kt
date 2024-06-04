package com.duyvv.android

import com.duyvv.android.base.BaseActivity
import com.duyvv.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val context = this@MainActivity
}
