package fr.mm.lemementoboulangerie_0_1_0.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.mm.lemementoboulangerie_0_1_0.R
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother
import fr.mm.lemementoboulangerie_0_1_0.classes.inventory.InventoryElementCell
import fr.mm.lemementoboulangerie_0_1_0.classes.inventory.InventoryHorizontal
import fr.mm.lemementoboulangerie_0_1_0.staticClass.MaximeStaticClass

class MainActivity : AppCompatActivity() {
    private var context: Context? = null
    private var recipeViewerActivityButton: Button? = null
    private var inventoryLinearLayout: LinearLayout? = null
    private val onClickListener : InventoryElementCell.OnClickListener =
        object : InventoryElementCell.OnClickListener {
        override fun onClick(elementId: Int) {
            MaximeStaticClass.getMaxElementMotherSelected = MaxElementMother(elementId)
            val intent = Intent(context, RecipeViewerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        MaximeStaticClass.activityEnumInt = MaximeStaticClass.enumActivity.main.ordinal.toByte()

        // FindViewByID :
        recipeViewerActivityButton = findViewById(R.id.mainActivity_recipeViewActivity_Button)
        inventoryLinearLayout = findViewById(R.id.mainActivity_inventory_LinearLayout)

        // OnCLickListener :
        recipeViewerActivityButton!!.setOnClickListener {
            Toast.makeText(
                context,
                getString(R.string.work_in_progress_text),
                Toast.LENGTH_SHORT
            ).show()
        }

        // INVENTORY :
        val inventoryHorizontal = InventoryHorizontal(3)
        inventoryHorizontal.getAddElementToInventory(MaxElementMother.CROISSANT, 1, onClickListener)
        inventoryHorizontal.getAddElementToInventory(
            MaxElementMother.PAIN_CHOCOLAT,
            1,
            onClickListener
        )
        inventoryHorizontal.getAddElementToInventory(MaxElementMother.PALMIER, 1, onClickListener)
        inventoryLinearLayout!!.removeAllViews()
        inventoryLinearLayout!!.addView(
            inventoryHorizontal.getLinearLayoutInventoryWithoutQuantity(
                context
            )
        )
    }

    // ANDROID CELLPHONE NATIVE OPTION PART :
    override fun onBackPressed() {
        System.exit(0)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}