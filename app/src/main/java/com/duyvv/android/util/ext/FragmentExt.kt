package com.duyvv.android.util.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String? = fragment::class.java.simpleName
) {
    activity?.supportFragmentManager?.apply {
        beginTransaction().apply {
            if (addToBackStack) {
                addToBackStack(tag)
            }
            add(containerId, fragment, tag)
        }.commit()
    }
}

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean,
    tag: String? = fragment::class.java.simpleName
) {
    activity?.supportFragmentManager?.apply {
        beginTransaction().apply {
            if (addToBackStack) {
                addToBackStack(tag)
            }
            replace(containerId, fragment, tag)
        }.commit()
    }
}

fun Fragment.goBackFragment(): Boolean {
    activity?.supportFragmentManager?.notNull {
        val isShowPreviousPage = it.backStackEntryCount > 0
        if (isShowPreviousPage) {
            it.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
    return false
}
