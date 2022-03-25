package fr.mm.lemementoboulangerie_0_1_0.classes.inventory

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import fr.mm.lemementoboulangerie_0_1_0.R
import fr.mm.lemementoboulangerie_0_1_0.bibliotheques.MaximeToolsLayoutV1
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother

class InventoryElementCell {
    // INTERFACES :
    interface OnClickListener {
        fun onClick(elementId: Int)
    }

    // VARIABLES :
    var maxElementMother: MaxElementMother? = null
    var quantity = 0
        private set
    private var isClicked = false
    private var clickListener: OnClickListener =
        object : OnClickListener {
            override fun onClick(elementId: Int) {

            }
        }

    // METHODS :
    private fun emptyCell(context: Context, linearLayoutToReturn: LinearLayout) {
        // ElementVOID ImageView :
        val image = ImageView(context)
        image.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        image.setImageResource(R.drawable.ic_baseline_menu_book_white_80)
        linearLayoutToReturn.addView(image)

        // ElementVOID NameTextView :
        val nameTextView = TextView(context)
        nameTextView.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        nameTextView.gravity = Gravity.CENTER
        nameTextView.setPadding(5, 5, 5, 5)
        nameTextView.setTextColor(context.getColor(R.color.white))
        nameTextView.textSize = 14f
        nameTextView.text = context.getString(R.string.empty)
        linearLayoutToReturn.addView(nameTextView)

        // linearLayoutToReturn => OnClickListener :
        linearLayoutToReturn.setOnClickListener {
            isClicked = !isClicked
            if (isClicked) {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_02
                )
                image.setImageResource(R.drawable.ic_baseline_menu_book_80)
                nameTextView.setTextColor(context.getColor(R.color.black))
            } else {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_01
                )
                image.setImageResource(R.drawable.ic_baseline_menu_book_white_80)
                nameTextView.setTextColor(context.getColor(R.color.white))
            }
        }
    }

    private fun elementCell(context: Context, linearLayoutToReturn: LinearLayout) {
        // Element ImageView :
        val image = ImageView(context)
        image.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        image.setImageResource(getElementRIntDrawable())
        linearLayoutToReturn.addView(image)

        // Element NameTextView :
        val nameTextView = TextView(context)
        nameTextView.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        nameTextView.gravity = Gravity.CENTER
        nameTextView.setPadding(5, 5, 5, 5)
        nameTextView.setTextColor(context.getColor(R.color.white))
        nameTextView.textSize = 14f
        nameTextView.text = getElementName(context)
        linearLayoutToReturn.addView(nameTextView)

        // Quantity TextView :
        val quantityTextView = TextView(context)
        quantityTextView.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        quantityTextView.gravity = Gravity.CENTER
        quantityTextView.setPadding(5, 5, 5, 5)
        quantityTextView.setTextColor(context.getColor(R.color.black))
        quantityTextView.textSize = 14f
        quantityTextView.text = quantity.toString()
        linearLayoutToReturn.addView(quantityTextView)

        // linearLayoutToReturn => OnClickListener :
        linearLayoutToReturn.setOnClickListener {
            isClicked = !isClicked
            if (isClicked) {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_02
                )
                nameTextView.setTextColor(context.getColor(R.color.black))
            } else {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_01
                )
                nameTextView.setTextColor(context.getColor(R.color.white))
            }
            clickListener.onClick(getElementID())
        }
        linearLayoutToReturn.setOnLongClickListener {
            isClicked = true
            setRemoveQuantity(10)
            if (quantity == 0) {
                resetToEmptyCell(context, linearLayoutToReturn)
            } else {
                quantityTextView.text = quantity.toString()
            }
            false
        }
    }

    private fun elementCellWithoutQuantity(context: Context, linearLayoutToReturn: LinearLayout) {
        // Element ImageView :
        val image = ImageView(context)
        image.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, 270, 270,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        image.setImageResource(getElementRIntDrawable())
        linearLayoutToReturn.addView(image)

        // Element NameTextView :
        val nameTextView = TextView(context)
        nameTextView.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, 270, LinearLayout.LayoutParams.WRAP_CONTENT,
            0, 0, 0, 0, Gravity.CENTER, 0f
        )
        nameTextView.gravity = Gravity.CENTER
        nameTextView.setPadding(5, 5, 5, 5)
        nameTextView.setTextColor(context.getColor(R.color.white))
        nameTextView.textSize = 18f
        nameTextView.text = getElementName(context)
        linearLayoutToReturn.addView(nameTextView)

        // linearLayoutToReturn => OnClickListener :
        linearLayoutToReturn.setOnClickListener {
            isClicked = !isClicked
            if (isClicked) {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_02
                )
                nameTextView.setTextColor(context.getColor(R.color.black))
            } else {
                linearLayoutToReturn.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.button_background_01
                )
                nameTextView.setTextColor(context.getColor(R.color.white))
            }
            clickListener.onClick(getElementID())
        }
    }

    private fun resetToEmptyCell(context: Context, linearLayoutToReturn: LinearLayout) {
        maxElementMother = null
        quantity = 0
        linearLayoutToReturn.removeAllViews()
        emptyCell(context, linearLayoutToReturn)
    }

    fun getCellLinearLayout(context: Context): LinearLayout {

        // LinearLayoutToReturn :
        val linearLayoutToReturn = LinearLayout(context)
        linearLayoutToReturn.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context, 300, 400, 5, 5, 5, 5, Gravity.CENTER, 0f
        )
        linearLayoutToReturn.gravity = Gravity.CENTER
        linearLayoutToReturn.background =
            ContextCompat.getDrawable(context, R.drawable.button_background_01)
        linearLayoutToReturn.orientation = LinearLayout.VERTICAL
        linearLayoutToReturn.setPadding(15, 15, 15, 15)

        // Si CASE REMPLIE :
        if (maxElementMother != null) {
            elementCell(context, linearLayoutToReturn)
        } else {
            emptyCell(context, linearLayoutToReturn)
        }
        return linearLayoutToReturn
    }

    fun getCellLinearLayoutWithoutQuantityForHorizontalUse(context: Context): LinearLayout {

        // LinearLayoutToReturn :
        val linearLayoutToReturn = LinearLayout(context)
        linearLayoutToReturn.layoutParams = MaximeToolsLayoutV1.setLayoutParams(
            context,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            5,
            5,
            5,
            5,
            Gravity.CENTER,
            0f
        )
        linearLayoutToReturn.gravity = Gravity.CENTER
        linearLayoutToReturn.background =
            ContextCompat.getDrawable(context, R.drawable.button_background_01)
        linearLayoutToReturn.orientation = LinearLayout.VERTICAL
        linearLayoutToReturn.setPadding(15, 15, 15, 15)

        // Si CASE REMPLIE :
        if (maxElementMother != null) {
            elementCellWithoutQuantity(context, linearLayoutToReturn)
        } else {
            emptyCell(context, linearLayoutToReturn)
        }
        return linearLayoutToReturn
    }

    fun getElementID(): Int {
        return maxElementMother!!.elementID
    }

    fun setMaxElementMother(maxElementMother: MaxElementMother?, onClickListener: OnClickListener) {
        this.maxElementMother = maxElementMother
        clickListener = onClickListener
    }

    fun getElementName(context: Context?): String {
        return maxElementMother!!.getName(context!!)
    }

    fun getElementRIntDrawable(): Int {
        return maxElementMother!!.elementRIntDrawable
    }

    fun setAddQuantity(quantityAdd: Int) {
        if (quantity >= 999999999) {
            quantity = 999999999
            return
        } else if (quantity + quantityAdd >= 999999999) {
            quantity = 999999999
            return
        }
        val i = quantity + quantityAdd
        if (i < 0) {
            quantity = 0
        } else {
            quantity += quantityAdd
        }
    }

    fun setRemoveQuantity(quantityDecrease: Int) {
        val i = quantity - quantityDecrease
        if (i <= 0) {
            quantity = 0
            maxElementMother = null
        } else {
            quantity -= quantityDecrease
        }
    }
}