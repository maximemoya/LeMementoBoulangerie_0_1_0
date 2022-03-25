package fr.mm.lemementoboulangerie_0_1_0.classes.inventory

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import fr.mm.lemementoboulangerie_0_1_0.R
import fr.mm.lemementoboulangerie_0_1_0.bibliotheques.MaximeToolsLayoutV1
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother
import java.util.ArrayList

class InventoryPlayer(inventoryCellListSize: Int) {
    // VARIABLES :
    private var inventoryElementCellList: MutableList<InventoryElementCell> = ArrayList()

    // CONSTRUCTOR :
    init {
        for (i in 0 until inventoryCellListSize) {
            inventoryElementCellList.add(InventoryElementCell())
        }
    }

    // METHODS :
    fun getLinearLayoutInventory(context: Context?): LinearLayout {
        val linearLayoutToReturn = LinearLayout(context)
        linearLayoutToReturn.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context!!, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        linearLayoutToReturn.orientation = LinearLayout.VERTICAL
        linearLayoutToReturn.setPadding(5, 15, 5, 15)
        linearLayoutToReturn.background =
            ContextCompat.getDrawable(context, R.drawable.button_background_03)
        var i = 0
        while (i < inventoryElementCellList.size) {
            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
                context,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0,
                0,
                0,
                0,
                Gravity.START,
                0f
            )
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            if (inventoryElementCellList.size - i > 3) {
                for (j in 0..2) {
                    linearLayout.addView(inventoryElementCellList[i + j].getCellLinearLayout(context))
                }
            } else {
                for (j in 0 until inventoryElementCellList.size - i) {
                    linearLayout.addView(inventoryElementCellList[i + j].getCellLinearLayout(context))
                }
            }
            linearLayoutToReturn.addView(linearLayout)
            i += 3
        }
        return linearLayoutToReturn
    }

    fun getAddElementToInventory(
        context: Context?,
        maxElementMotherID: Int,
        quantity: Int,
        inventoryLinearLayout: LinearLayout
    ) {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID()) {
                    cell.setAddQuantity(quantity)
                    inventoryLinearLayout.removeAllViews()
                    inventoryLinearLayout.addView(getLinearLayoutInventory(context))
                    return
                }
            }
        }
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother == null) {
                cell.maxElementMother = MaxElementMother(maxElementMotherID)
                cell.setAddQuantity(quantity)
                inventoryLinearLayout.removeAllViews()
                inventoryLinearLayout.addView(getLinearLayoutInventory(context))
                return
            }
        }
    }

    fun getAddElementToInventory(maxElementMotherID: Int, quantity: Int) {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID()) {
                    cell.setAddQuantity(quantity)
                    return
                }
            }
        }
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother == null) {
                cell.maxElementMother = MaxElementMother(maxElementMotherID)
                cell.setAddQuantity(quantity)
                return
            }
        }
    }

    fun getRemoveElementToInventory(
        context: Context?,
        maxElementMotherID: Int,
        quantity: Int,
        inventoryLinearLayout: LinearLayout
    ) {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID()) {
                    cell.setRemoveQuantity(quantity)
                    inventoryLinearLayout.removeAllViews()
                    inventoryLinearLayout.addView(getLinearLayoutInventory(context))
                    return
                }
            }
        }
    }

    fun getRemoveElementToInventory(context: Context?, maxElementMotherID: Int, quantity: Int) {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID()) {
                    cell.setRemoveQuantity(quantity)
                    return
                }
            }
        }
    }

    private fun checkElementIsInInventory(maxElementMotherID: Int, quantity: Int): Boolean {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID() && cell.quantity >= quantity) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkEmptyCell(): Boolean {
        for (cell in inventoryElementCellList) {
            if (cell.maxElementMother == null) {
                return true
            }
        }
        return false
    }

    // GETTER / SETTER :
    fun getInventoryElementCellList(): List<InventoryElementCell> {
        return inventoryElementCellList
    }

    fun setInventoryElementCellList(inventoryElementCellList: MutableList<InventoryElementCell>) {
        this.inventoryElementCellList = inventoryElementCellList
    }

    fun setAddInventoryCellList(inventoryElementCellToAdd: InventoryElementCell) {
        inventoryElementCellList.add(inventoryElementCellToAdd)
    }

    fun setReplaceInventoryCellList(index: Int, inventoryElementCell: InventoryElementCell) {
        inventoryElementCellList.add(index, inventoryElementCell)
    }

}