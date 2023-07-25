package com.roshka.lanzadados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Obtener controles de la vista */
        val buttonLanzar: Button = findViewById(R.id.button_lanzar)
        val imageDados: ImageView = findViewById(R.id.image_dados)
        val textTitleJugada: TextView = findViewById(R.id.text_title_jugada)
        val textJugada: TextView = findViewById(R.id.text_jugada)
        val textResultado: TextView = findViewById(R.id.text_resultado)

        val start = 1
        val end = 6
        var result: MutableList<Int> = mutableListOf()
        var attempts = 0


        buttonLanzar.setOnClickListener {

            if (attempts < 5) {
                val numeroAleatorio = rand(start, end)
                result.add(numeroAleatorio)
                textJugada.text = result.toString()
                textJugada.visibility = View.VISIBLE
                textTitleJugada.visibility = View.VISIBLE

                when (numeroAleatorio) {
                    1 -> imageDados.setImageResource(R.drawable.dice_1)
                    2 -> imageDados.setImageResource(R.drawable.dice_2)
                    3 -> imageDados.setImageResource(R.drawable.dice_3)
                    4 -> imageDados.setImageResource(R.drawable.dice_4)
                    5 -> imageDados.setImageResource(R.drawable.dice_5)
                    6 -> imageDados.setImageResource(R.drawable.dice_6)
                }

                attempts++

                if (attempts == 5) {
                    textResultado.text = checkPlay(result)
                    println(textResultado.text)
                    println(textResultado.text.contains("PROXIMA"))

                    if (textResultado.text.contains("PROXIMA")) textResultado.setTextColor(
                        getColor(
                            R.color.red
                        )
                    ) else textResultado.setTextColor(getColor(R.color.green))
                    textResultado.visibility = View.VISIBLE
                    buttonLanzar.text = "Jugar de nuevo"
                }

            } else {
                /* Jugar de nuevo */
                buttonLanzar.text = "Lanzar"
                textJugada.text = ""
                attempts = 0
                result = mutableListOf()
                textResultado.visibility = View.INVISIBLE
                textTitleJugada.visibility = View.INVISIBLE
                imageDados.setImageResource(R.drawable.empty_dice)
            }
        }

    }

    val rand = { start: Int, end: Int -> (start..end).random() }

    private fun checkPlay(result: MutableList<Int>): String {
        var message: String = ""

        /* Cuatro dados con el mismo valor. */
        for (item in result.distinct()) {
            /* Todos los dados, con el mismo valor. GENERALA*/
            if (result.count { it == item } == 5) {
                message = "GENERALA"
                break
            } else if (result.count { it == item } == 4) {
                /* Cuatro dados con el mismo valor. */
                message = "POKER"
                break
            } else if (result.count { it == item } == 3) {
                /* 3 con un mismo valor y 2 con mismo valor .*/
                for (item in result.distinct()) {
                    if (result.count { it == item } == 2) {
                        message = "FULL"
                        break
                    }
                }
            }
        }

        if (message == "") {
            message = if (playStairs(result)) "ESCALERA" else "LA PROXIMA SALDRÁ"

        }

        return message
    }

    private fun playStairs(result: MutableList<Int>): Boolean {
        var posibilities = listOf<String>("12345", "23456", "34561", "54321", "65432", "16543")
        if (posibilities.contains(result.joinToString())) return true
        return false
    }


}