package com.example.customviewtest.fragments.stars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customviewtest.views.surface.stars.StarsSurfaceView

class StartsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = StarsSurfaceView(requireContext())
}
