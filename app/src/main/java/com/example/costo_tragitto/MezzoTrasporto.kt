package com.example.costo_tragitto

open class MezzoTrasporto(protected val marca: String,
                          protected val kmPercorsi: Float){

    init {
        require(kmPercorsi >= 0) { "I km non possono essere negativi" }
    }

    open fun info(): String{
        return "marca: $marca   km percorsi: $kmPercorsi"
    }

    open fun calcolaCostoTotale(): Float = 0.0f
}

class AutoBenzina(marca: String,
                  kmPercorsi: Float,
                  private val kmAlLitro: Float,
                  private val prezzoBenzinaLitro: Float
) : MezzoTrasporto(marca, kmPercorsi)
{
    override fun calcolaCostoTotale(): Float {
        val litriConsumati = kmPercorsi / kmAlLitro
        return litriConsumati * prezzoBenzinaLitro
    }
}

class AutoElettrica(
    marca: String,
    kmPercorsi: Float,
    private val kwhPer100Km: Float,
    private val prezzoCorrenteKwh: Float
) : MezzoTrasporto(marca, kmPercorsi) {

    override fun calcolaCostoTotale(): Float {
        val kwhEnergiaConsumata = (kmPercorsi / 100.0f) * kwhPer100Km
        return kwhEnergiaConsumata * prezzoCorrenteKwh
    }
}

// Sottoclasse: Bici Elettrica
class BiciElettrica(
    marca: String,
    kmPercorsi: Float,
    private val livelloAssistenza: Int,
    private val prezzoCorrenteKwh: Float
) : MezzoTrasporto(marca, kmPercorsi) {

    override fun calcolaCostoTotale(): Float{
        val consumoEnergiaKm = when (livelloAssistenza){
            0 -> 0.0f
            1 -> 10.0f
            2 -> 15.0f
            3 -> 20.0f
            else -> 0.0f
        }
        val kwhTot = (consumoEnergiaKm * kmPercorsi) / 1000.0f
        return kwhTot * prezzoCorrenteKwh

    }
}
