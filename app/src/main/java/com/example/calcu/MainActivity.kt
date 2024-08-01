package com.example.calcu

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class MainActivity : AppCompatActivity() {

    var operacionActual = ""

    var primerNum:Double = Double.NaN
    var secondNum:Double = Double.NaN

    lateinit var tvtemp:TextView
    lateinit var tvResult:TextView

    lateinit var formatoDecimal:DecimalFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("#.########")
        tvtemp = findViewById(R.id.TVtem)
        tvResult = findViewById(R.id.TVact)

    }
    fun cambiarOperador(b: View){
        if(tvtemp.text.isNotEmpty() || primerNum.toString()!="NaN") {
            calcular()
            val boton: Button = b as Button
            if (boton.text.toString().trim() == "÷") {
                operacionActual = "/"
            } else if (boton.text.toString().trim() == "x") {
                operacionActual = "*"
            } else {
                operacionActual = boton.text.toString().trim()
            }
            tvResult.text = formatoDecimal.format(primerNum) + operacionActual
            tvtemp.text = ""

        }
    }

    fun calcular(){
        try {
            if(primerNum.toString()!="NaN"){
                if(tvtemp.text.toString().isEmpty()){
                    tvtemp.text = tvResult.text.toString()
                }
                secondNum = tvtemp.text.toString().toDouble()
                tvtemp.text=""

                when(operacionActual){
                    "+"-> primerNum = (primerNum+secondNum)
                    "-"-> primerNum = (primerNum-secondNum)
                    "*"-> primerNum = (primerNum*secondNum)
                    "/"-> primerNum = (primerNum/secondNum)
                }
            }else{
                primerNum = tvtemp.text.toString().toDouble()
            }
        }catch (e:Exception){

        }
    }

    fun optrigo (b: View){
        val boton: Button = b as Button
        when(boton.text.toString().trim()){
            "sen" -> operacionActual= "sin"
            "cos" -> operacionActual= "cos"
            "tag" -> operacionActual= "tan"
        }
        tvtemp.text = operacionActual + "("

    }

    fun seleccionarNumero(b: View){
        val boton:Button = b as Button
        tvtemp.text = tvtemp.text.toString() + boton.text.toString()
    }

    fun igual(b: View){
        println(operacionActual)
        if(operacionActual == "sin" || operacionActual == "cos" || operacionActual == "tan"){
            val tempo = tvtemp.text.toString().split("(")
            println(tempo[1])
            primerNum = tempo[1].toDouble()
            when(operacionActual){
                "sin"-> primerNum = (sin(primerNum * Math.PI / 180))
                "cos"-> primerNum = (cos(primerNum * Math.PI / 180))
                "tan"-> primerNum = (tan(primerNum * Math.PI / 180))
            }
        }else {
            calcular()
        }
        tvResult.text = formatoDecimal.format(primerNum)
        //primerNumero = Double.NaN
        operacionActual = ""
    }
    fun borrar(b:View){
        val boton:Button = b as Button
        if(boton.text.toString().trim()=="C"){

            if(tvtemp.text.toString().isNotEmpty()){
                var datosActuales:CharSequence = tvtemp.text as CharSequence
                tvtemp.text = datosActuales.subSequence(0,datosActuales.length-1)
            }else{
                primerNum = Double.NaN
                secondNum = Double.NaN
                tvtemp.text = ""
                tvResult.text = ""
            }
        }else if(boton.text.toString().trim()=="CA"){
            primerNum = Double.NaN
            secondNum = Double.NaN
            tvtemp.text = ""
            tvResult.text = ""
        }

    }
}