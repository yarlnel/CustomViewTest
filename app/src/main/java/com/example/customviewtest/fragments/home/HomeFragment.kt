package com.example.customviewtest.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.customviewtest.R
import com.example.customviewtest.databinding.FragmentHomeBinding
import com.example.customviewtest.fragments.drawer.DrawerFragment
import com.example.customviewtest.fragments.stars.StartsFragment
import com.example.customviewtest.fragments.surface_animations.SurfaceAnimationsFragment
import com.example.customviewtest.utils.navigateTo

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnDrawer.setOnClickListener {
                navigateTo(DrawerFragment())
            }

            btnStars.setOnClickListener {
                navigateTo(StartsFragment())
            }

            btnSurfaceAnimations.setOnClickListener {
                navigateTo(SurfaceAnimationsFragment())
            }
        }
    }
}