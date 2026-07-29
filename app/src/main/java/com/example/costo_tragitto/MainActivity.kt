package com.example.costo_tragitto

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Riferimenti UI
        val mainView = findViewById<View>(R.id.main)
        val menu = findViewById<Spinner>(R.id.spinnerVeicoli)
        val parametro1 = findViewById<TextView>(R.id.textViewParam1)
        val valore1 = findViewById<EditText>(R.id.editTextParam1)
        val parametro2 = findViewById<TextView>(R.id.textViewParam2)
        val valore2 = findViewById<EditText>(R.id.editTextParam2)
        val editMarca = findViewById<EditText>(R.id.editTextMarca)
        val editKm = findViewById<EditText>(R.id.editTextKm)
        val invio = findViewById<Button>(R.id.buttonCalcola)
        val risultato = findViewById<TextView>(R.id.risultato)

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Spinner
        val opzioneVeicoli = listOf("Auto Benzina", "Auto Elettrica", "Bici Elettrica")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opzioneVeicoli)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        menu.adapter = adapter

        // Gestione cambio selezione
        menu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> { // Auto Benzina
                        parametro1.text = "Km con un Litro"
                        valore1.hint = "es. 15.0"
                        parametro2.text = "Prezzo Benzina (€/L)"
                        valore2.hint = "es. 1.85"
                    }
                    1 -> { // Auto Elettrica
                        parametro1.text = "Consumo (kWh/100km)"
                        valore1.hint = "es. 18.0"
                        parametro2.text = "Prezzo Corrente (€/kWh)"
                        valore2.hint = "es. 0.25"
                    }
                    2 -> { // Bici Elettrica
                        parametro1.text = "Livello Assistenza (1-3)"
                        valore1.hint = "1, 2 o 3"
                        parametro2.text = "Prezzo Corrente (€/kWh)"
                        valore2.hint = "es. 0.25"
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Bottone Calcola
        invio.setOnClickListener {
            try {
                val marca = editMarca.text.toString()
                val km = editKm.text.toString().toFloat()
                val v1 = valore1.text.toString().toFloat()
                val v2 = valore2.text.toString().toFloat()

                if (marca.isEmpty()) {
                    Toast.makeText(this, "Inserisci la marca", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val veicolo: MezzoTrasporto = when (menu.selectedItemPosition) {
                    0 -> AutoBenzina(marca, km, v1, v2)
                    1 -> AutoElettrica(marca, km, v1, v2)
                    2 -> BiciElettrica(marca, km, v1.toInt(), v2)
                    else -> MezzoTrasporto(marca, km)
                }

                val costo = veicolo.calcolaCostoTotale()
                risultato.text = "Il viaggio con $marca costa: ${String.format("%.2f", costo)}€"

            } catch (e: Exception) {
                Toast.makeText(this, "Per favore, inserisci tutti i dati numerici correttamente", Toast.LENGTH_SHORT).show()
                risultato.text = "Errore nei dati"
            }
        }
    }
}