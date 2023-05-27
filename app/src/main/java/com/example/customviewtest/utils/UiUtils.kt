package com.example.customviewtest.utils

import androidx.fragment.app.Fragment
import com.example.customviewtest.R


fun Fragment.navigateTo(
    fragment: Fragment,
    tag: String = fragment::class.java.name
) {
    requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.root, fragment)
        .addToBackStack(tag + "_back")
        .commit()
}
