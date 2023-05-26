package com.example.customviewtest.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.customviewtest.R
import com.example.customviewtest.databinding.FragmentHomeBinding
import com.example.customviewtest.fragments.drawer.DrawerFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnDrawer.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.root, DrawerFragment())
                    .addToBackStack("drawer_back")
                    .commit()
            }
        }
    }
}