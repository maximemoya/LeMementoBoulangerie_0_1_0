package fr.mm.lemementoboulangerie_0_1_0.classes.inventory

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import fr.mm.lemementoboulangerie_0_1_0.bibliotheques.MaximeToolsLayoutV1
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother
import java.util.ArrayList

class InventoryHorizontal {
    // VARIABLES :
    private var inventoryElementCellList: MutableList<InventoryElementCell> = ArrayList()

    // CONSTRUCTOR :
    constructor(inventoryCellListSize: Int) {
        for (i in 0 until inventoryCellListSize) {
            inventoryElementCellList.add(InventoryElementCell())
        }
    }

    constructor(inventoryElementCellList: MutableList<InventoryElementCell>) {
        this.inventoryElementCellList = inventoryElementCellList
    }

    // METHODS :
    fun getLinearLayoutInventoryWithoutQuantity(context: Context?): LinearLayout {
        val linearLayoutToReturn = LinearLayout(context)
        linearLayoutToReturn.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context!!, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        linearLayoutToReturn.orientation = LinearLayout.HORIZONTAL
        for (cell in inventoryElementCellList) {
            linearLayoutToReturn.addView(
                cell.getCellLinearLayoutWithoutQuantityForHorizontalUse(
                    context
                )
            )
        }
        return linearLayoutToReturn
    }

    fun getAddElementToInventory(
        context: Context?,
        maxElementMotherID: Int,
        quantity: Int,
        inventoryLinearLayout: LinearLayout,
        onClickListener: InventoryElementCell.OnClickListener?
    ) {
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother != null) {
                if (maxElementMotherID == cell.getElementID()) {
                    cell.setAddQuantity(quantity)
                    inventoryLinearLayout.removeAllViews()
                    inventoryLinearLayout.addView(getLinearLayoutInventoryWithoutQuantity(context))
                    return
                }
            }
        }
        for (cell in getInventoryElementCellList()) {
            if (cell.maxElementMother == null) {
                cell.setMaxElementMother(MaxElementMother(maxElementMotherID), onClickListener!!)
                cell.setAddQuantity(quantity)
                inventoryLinearLayout.removeAllViews()
                inventoryLinearLayout.addView(getLinearLayoutInventoryWithoutQuantity(context))
                return
            }
        }
    }

    fun getAddElementToInventory(
        maxElementMotherID: Int,
        quantity: Int,
        onClickListener: InventoryElementCell.OnClickListener?
    ) {
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
                cell.setMaxElementMother(MaxElementMother(maxElementMotherID), onClickListener!!)
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
                    inventoryLinearLayout.addView(getLinearLayoutInventoryWithoutQuantity(context))
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