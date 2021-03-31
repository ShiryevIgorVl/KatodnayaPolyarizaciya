package com.example.katodnayapolyarizaciya.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.katodnayapolyarizaciya.Constans.Constans
import com.example.katodnayapolyarizaciya.databinding.ActivityResultBinding

class ActivityResult : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val constants = Constans

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.corrent.text = constants.I + intent.getStringExtra(constants.I)
        binding.resistanceR.text = constants.RR + intent.getStringExtra(constants.RR)
        binding.resistanceT.text = constants.RT + intent.getStringExtra(constants.RT)
        binding.Utz.text = constants.UTZ + intent.getStringExtra(constants.UTZ)
    }
}