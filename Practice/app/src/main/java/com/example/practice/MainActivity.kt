package com.example.practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment=FragmentOne()
        val secondFragment=FragmentTwo()



        binding.btnfrag1.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,firstFragment)
                addToBackStack("frag2null")
                commit()
            }
        }
        binding.btnfrag2.setOnClickListener(){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,secondFragment)
                addToBackStack(null)
                commit()
            }
        }

    }
}