package com.example.katodnayapolyarizaciya.Logic

import android.util.Log
import android.util.Log.d
import android.view.View
import com.example.katodnayapolyarizaciya.Constans.Constans
import com.example.katodnayapolyarizaciya.UI.InputActivity
import kotlin.math.*

class Logic {
    private val constans = Constans

    //Продольное сопротивление стального трубопровода Rт , Ом/м
    fun longitudinalResistanceOfSteelPipe(du: Double, wallThickness: Double): Double {
        val rT =
            constans.STEEL_RESISTANCE / (PI * ((du - wallThickness)) * (wallThickness))
        return rT
    }

    //Характеристическое сопротивление трубопровода Z, Ом
    fun characteristicResistanceOfPipeline(
        rT: Double,
        rR: Double,
        resistanceIP: Double,
        du: Double
    ): Double {
        val Z =
            sqrt((rT * (resistanceIP + rR)) / (PI * (du / constans.PRIVEDENIE_K_METRAM)))
        d("Rip", "Характеристическое сопротивление = $Z")
        return Z
    }

    //Постоянная распространения тока A, 1/м
    fun currentPropagationConstant(
        du: Double,
        rT: Double,
        rR: Double,
        resistanceIP: Double
    ): Double {
        val Al =
            sqrt((PI * (du / constans.PRIVEDENIE_K_METRAM) * rT) / (rR + resistanceIP))
        d("Rip", "Постоянная распространения тока = $Al")
        return Al
    }

    //Сопротивление растеканию не изолированного трубопровода Rp (Ом·м^2)
    fun pipelineSpreadingResistance(UES: Double, du: Double, depth: Double, rT: Double): Double {
        var n = 0
        var rR = 0.0
        var rR1 = UES

        d("Rip", "Ро = $UES")
        d("Rip", "диаметр = $du")
        d("Rip", "глубина = $depth")
        d("Rip", "продольное сопротивление = $rT")

        while (n < 20) {
            n++
            rR =
                ((UES * (du / constans.PRIVEDENIE_K_METRAM)) / 2) * ln((0.4 * rR1) / (((du / constans.PRIVEDENIE_K_METRAM) * (du / constans.PRIVEDENIE_K_METRAM) * depth * rT)))
            val eps = abs(rR - rR1)
            if (eps <= 0.2) {
                break
            } else {
                rR1 = rR
            }
            d("Rip", "сопротивление растеканию = $rR")
        }
        return rR
    }

    //Смещение потенциала с омической составляющей Uтз, В
    fun potentialOffset(lEngth: Double, resistanceIP: Double, rR: Double): Double {
        val Uts: Double
        if (lEngth >= 4000) {
            Uts =
                constans.UTS_BOLEE_4KM * (1 + (rR / resistanceIP))
        } else {
            Uts =
                constans.UTS_DO_4KM * (1 + (rR / resistanceIP))
        }
        d("Rip", "Смещение потенциала = $Uts")
        return Uts
    }

    //Сила тока I , А
    fun current(
        lEngth: Double,
        du: Double,
        resistanceIP: Double,
        Uts: Double,
        Z: Double,
        Al: Double,
        rR: Double
    ): Double {
        val I: Double
        if (lEngth >= 4000) {
            I = (Uts / Z) * sinh(Al * lEngth)
        } else {
            I = (PI * lEngth * Uts * (du / constans.PRIVEDENIE_K_METRAM)) / (resistanceIP + rR)
        }
        d("Rip", "Сила тока = $I")

        return I
    }


}