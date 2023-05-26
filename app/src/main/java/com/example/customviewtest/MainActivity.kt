package com.example.customviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewtest.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.root, HomeFragment())
            .addToBackStack("home_back")
            .commit()
    }
}