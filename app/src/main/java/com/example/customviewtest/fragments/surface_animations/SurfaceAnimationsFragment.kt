package com.example.customviewtest.fragments.surface_animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customviewtest.views.surface.AnimationSurfaceView

class SurfaceAnimationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AnimationSurfaceView(requireContext())
}
