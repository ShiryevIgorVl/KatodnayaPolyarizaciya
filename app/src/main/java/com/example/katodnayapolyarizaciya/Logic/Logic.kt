package com.example.katodnayapolyarizaciya.Logic

import android.view.View
import com.example.katodnayapolyarizaciya.Constans.Constans
import com.example.katodnayapolyarizaciya.UI.InputActivity
import kotlin.math.*

class Logic {
    private val inputActivity = InputActivity()
    private val constans = Constans

    //Продольное сопротивление стального трубопровода Rт , Ом/м
    fun longitudinalResistanceOfSteelPipe(): Double {
        val rT =
            constans.STEEL_RESISTANCE / (Math.PI * ((inputActivity.du - inputActivity.wallThickness) / constans.PRIVEDENIE_K_METRAM) * (inputActivity.wallThickness / constans.PRIVEDENIE_K_METRAM))
        return rT
    }

    //Характеристическое сопротивление трубопровода Z, Ом
    fun characteristicResistanceOfPipeline(): Double {
        val Z =
            sqrt((longitudinalResistanceOfSteelPipe() * (inputActivity.resistanceIP + pipelineSpreadingResistance())) / (PI * (inputActivity.du * constans.PRIVEDENIE_K_METRAM)))
        return Z
    }

    //постоянная распространения тока A, 1/м
    fun currentPropagationConstant(): Double {
        val A =
            sqrt((PI * (inputActivity.du * constans.PRIVEDENIE_K_METRAM) * longitudinalResistanceOfSteelPipe()) / (pipelineSpreadingResistance() + inputActivity.resistanceIP))
        return A
    }

    //Сопротивление растеканию не изолированного трубопровода Rp (Ом·м^2)
    fun pipelineSpreadingResistance(): Double {
        var n = 0
        var rR = 0.0
        var rR1 = inputActivity.UES
        while (n < 20) {
            n++
            rR =
                ((inputActivity.UES * (inputActivity.du / constans.PRIVEDENIE_K_METRAM)) / 2) * (ln(
                    0.4 * rR1
                ))
            var eps = abs(rR - rR1)
            if (eps <= 2) {
                break
            } else {
                rR1 = rR
            }
        }
        return rR
        println(rR)
    }

    //Смещение потенциала с омической составляющей Uтз, В
    fun potentialOffset(): Double {
        val Uts: Double
        if (inputActivity.lEngth >= 4000) {
            Uts =
                constans.UTS_BOLEE_4KM * (1 + (pipelineSpreadingResistance() / inputActivity.resistanceIP))
        } else {
            Uts =
                constans.UTS_DO_4KM * (1 + (pipelineSpreadingResistance() / inputActivity.resistanceIP))
        }
        return Uts
    }

    //Сила тока I , А
    fun current(): Double {
        val A: Double
        if (inputActivity.lEngth >= 4000) {
            A = (potentialOffset() / characteristicResistanceOfPipeline()) * sinh(
                currentPropagationConstant() * inputActivity.lEngth
            )
        } else {
            A = (PI*inputActivity.lEngth*potentialOffset()*(inputActivity.du/constans.PRIVEDENIE_K_METRAM)) / (inputActivity.resistanceIP + pipelineSpreadingResistance())
        }
        return A
    }


}