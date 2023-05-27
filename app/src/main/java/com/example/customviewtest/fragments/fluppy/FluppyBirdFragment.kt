package com.example.customviewtest.fragments.fluppy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customviewtest.views.surface.fluppy.FluppyBirdGameView

class FluppyBirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FluppyBirdGameView(requireContext())
}