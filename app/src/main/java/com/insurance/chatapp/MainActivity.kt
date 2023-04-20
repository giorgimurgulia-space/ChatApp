package com.insurance.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.insurance.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.top_user_fragment, TopUserFragment())
            add(R.id.bottom_user_fragment, BottomUserFragment())
        }.commit()
    }
}