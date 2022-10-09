package com.example.giphyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giphyapp.R
import com.example.giphyapp.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}