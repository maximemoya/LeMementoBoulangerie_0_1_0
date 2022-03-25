package fr.mm.lemementoboulangerie_0_1_0.classes

import android.content.Context
import fr.mm.lemementoboulangerie_0_1_0.R
import java.util.ArrayList

class MaxElementMother(idEnumReference: Int) {

    // STATICS class :
    companion object {
        // STATICS propres a la classe :
        private val rIntStringNameArray: IntArray = intArrayOf(
            R.string.croissant,
            R.string.pain_au_chocolat,
            R.string.palmier
        )
        private val rIntDrawableArray: IntArray = intArrayOf(
            R.drawable.px100_croissant,
            R.drawable.px100_pain_chocolat,
            R.drawable.px100_palmier
        )
        val urlLinkArray: Array<String> = arrayOf(
            "https://www.youtube.com/watch?v=nkqoOkg0BtY",
            "https://www.youtube.com/watch?v=8NJ5LQxlES0",
            "https://www.youtube.com/watch?v=nXuyB6zrp_g"
        )
        val CROISSANT: Int = enumID.croissant.ordinal
        val PAIN_CHOCOLAT: Int = enumID.pain_au_chocolat.ordinal
        val PALMIER: Int = enumID.palmier.ordinal

        // MaxElementMotherGlobalList :
        var getMaxElementMotherGlobalList: MutableList<MaxElementMother> = ArrayList()
        val initializeMaxElementMotherGlobalList: Unit
            get() {
                for (i in enumID.values().indices) {
                    getMaxElementMotherGlobalList.add(MaxElementMother(i))
                }
            }
    }

    // ENUM ID :
    enum class enumID {
        croissant, pain_au_chocolat, palmier
    }

    // VARIABLES objects :
    var elementID: Int = 0

    val elementRIntDrawable: Int
        get() = rIntDrawableArray[elementID]

    val urlLink: String
        get() {
            return urlLinkArray[elementID]
        }

    // CONSTRUCTOR :
    init {
        elementID = idEnumReference
    }

    // METHODS getters :
    fun getName(context: Context): String {
        return context.getString(
            rIntStringNameArray[elementID]
        )
    }

}