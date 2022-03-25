package fr.mm.lemementoboulangerie_0_1_0.staticClass

import android.media.MediaPlayer
import fr.mm.lemementoboulangerie_0_1_0.bibliotheques.MaximeToolsLayoutV1.timerInterface
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother

object MaximeStaticClass {

    // Inventory :
    var getMaxElementMotherSelected: MaxElementMother? = null
    var soundRIntRef = intArrayOf()
    var mediaPlayer: MediaPlayer? = null

    // THREAD INTERFACES :
    const val delayTimerMs: Short = 100
    private const val MAXTIMERINTERFACES: Short = 20
    var timerInterfaces = arrayOfNulls<timerInterface>(MAXTIMERINTERFACES.toInt())
    var activityEnumInt: Byte = 0
    var isMainActivityInitialised = false

    // Music and Sound :
    enum class enumSoundRIntRef {
        engineOnStart, engineOnWork, engineOnStop, musicIdle
    }

    // ACTIVITIES :
    enum class enumActivity {
        main, recipeViewer
    }
}