package com.example.katodnayapolyarizaciya.UI

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.katodnayapolyarizaciya.databinding.InputActivityBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: InputActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InputActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

    fun onClicСalculation(view: View){

    }
}