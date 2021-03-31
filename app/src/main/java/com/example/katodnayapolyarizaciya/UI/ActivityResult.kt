package com.example.katodnayapolyarizaciya.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.katodnayapolyarizaciya.Constans.Constans
import com.example.katodnayapolyarizaciya.R
import com.example.katodnayapolyarizaciya.databinding.ActivityResultBinding

class ActivityResult : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val constans = Constans

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.corrent.text = constans.I + intent.getStringExtra(constans.I)
        binding.resistanceR.text = constans.RR + intent.getStringExtra(constans.RR)
        binding.resistanceT.text = constans.RT + intent.getStringExtra(constans.RT)
        binding.Utz.text = constans.UTZ + intent.getStringExtra(constans.UTZ)
    }
}