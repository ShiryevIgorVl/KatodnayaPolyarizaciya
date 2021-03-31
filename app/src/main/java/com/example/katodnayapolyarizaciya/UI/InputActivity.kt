package com.example.katodnayapolyarizaciya.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.katodnayapolyarizaciya.Constans.Constans
import com.example.katodnayapolyarizaciya.Logic.Logic
import com.example.katodnayapolyarizaciya.databinding.InputActivityBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: InputActivityBinding
    private val constans = Constans

    var du = 0.0 // Диаметр трубопровода, мм
    var wallThickness = 0.0 //Толщина стенки трубы, мм
    var UES = 0.0 //Po
    var depth = 0.0//Глубина укдалки, м
    var lEngth = 0.0 //длинна участка, м
    var resistanceIP = 0.0 //сопротивление ИП, м

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InputActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }

    fun onClicСalculation(view: View) {

        val logic = Logic()

        du = binding.DuET.text.toString().toDouble() // Диаметр трубопровода, мм
        wallThickness =
            binding.wallThicknessET.text.toString().toDouble() //Толщина стенки трубы, мм
        UES = binding.UESET.text.toString().toDouble() //Po
        depth = binding.depthET.text.toString().toDouble() //Глубина укдалки, м
        lEngth = binding.lengthET.text.toString().toDouble() //длинна участка, м
        resistanceIP = binding.resistanceIPET.text.toString().toDouble() //сопротивление ИП, м

        val rT = logic.longitudinalResistanceOfSteelPipe(du, wallThickness)
        val rR = logic.pipelineSpreadingResistance(UES, du, depth, rT)
        val Z = logic.characteristicResistanceOfPipeline(rT, rR, resistanceIP, du)
        val Al = logic.currentPropagationConstant(du, rT, rR, resistanceIP)
        val Utz = logic.potentialOffset(lEngth, resistanceIP, rR)
        val I = logic.current(lEngth, du, resistanceIP, Utz, Z, Al, rR)

        val intent = Intent(this, ActivityResult::class.java)
        intent.putExtra(constans.I, I.toString())
        intent.putExtra(constans.RR, rR.toString())
        intent.putExtra(constans.RT, rT.toString())
        intent.putExtra(constans.UTZ, Utz.toString())

        startActivity(intent)
    }

    fun onClicClear(view: View) {
        binding.DuET.text.clear()
        binding.depthET.text.clear()
        binding.UESET.text.clear()
        binding.lengthET.text.clear()
        binding.resistanceIPET.text.clear()
        binding.wallThicknessET.text.clear()
    }
}