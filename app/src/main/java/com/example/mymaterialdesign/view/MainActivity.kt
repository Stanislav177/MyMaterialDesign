package com.example.mymaterialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance()).commit()
        }
    }
}