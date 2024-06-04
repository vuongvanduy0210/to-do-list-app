package com.duyvv.android.ui

import com.duyvv.android.MainActivity
import com.duyvv.android.R
import com.duyvv.android.base.BaseFragment
import com.duyvv.android.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : BaseFragment<FragmentBlankBinding>() {

    override val layoutRes = R.layout.fragment_blank

    private var activity: MainActivity? = null

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {

    }
}
