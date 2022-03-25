package fr.mm.lemementoboulangerie_0_1_0.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.mm.lemementoboulangerie_0_1_0.R
import fr.mm.lemementoboulangerie_0_1_0.classes.MaxElementMother
import fr.mm.lemementoboulangerie_0_1_0.staticClass.MaximeStaticClass
import java.util.*

class RecipeViewerActivity : AppCompatActivity() {
    // VARIABLES :
    private var context: Context? = null
    private var backArrowImageView: ImageView? = null
    private var recipeNameTextView: TextView? = null
    private var linkVideoTextView: TextView? = null
    private var recipeLinearLayout: LinearLayout? = null

    // ON CREATE :
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_viewer)
        context = this
        MaximeStaticClass.activityEnumInt = MaximeStaticClass.enumActivity.recipeViewer.ordinal.toByte()

        // FindViewByID :
        backArrowImageView = findViewById(R.id.recipeViewerActivity_backArrowImage_ImageView)
        recipeNameTextView = findViewById(R.id.recipeViewerActivity_recipeName_TextView)
        recipeLinearLayout = findViewById(R.id.recipeViewerActivity_recipePlace_LinearLayout)

        // INFLATER LAYOUT :
        layoutSelectorRecipe(MaximeStaticClass.getMaxElementMotherSelected!!.elementID)
        linkVideoTextView = findViewById(R.id.recipeViewerActivity_videoLink_TextView)

        // INIT VIEWS :
        recipeNameTextView!!.text = MaximeStaticClass.getMaxElementMotherSelected!!.getName(context as RecipeViewerActivity)
            .uppercase(Locale.getDefault())

        // OnClickListener :
        backArrowImageView!!.setOnClickListener { backButtonMethod() }
        linkVideoTextView!!.setOnClickListener {
            goToUrlinNewIntent(
                context!!,
                MaximeStaticClass.getMaxElementMotherSelected!!.urlLink
            )
        }
    }

    // My METHODS :
    private fun layoutSelectorRecipe(id: Int) {
        when (id) {
            MaxElementMother.CROISSANT -> {
                recipeLinearLayout = LinearLayout.inflate(
                    context,
                    R.layout.recipe_croissant,
                    recipeLinearLayout as ViewGroup?
                ) as LinearLayout
            }
            MaxElementMother.PAIN_CHOCOLAT -> {
                recipeLinearLayout = View.inflate(
                    context,
                    R.layout.recipe_painchocolat,
                    recipeLinearLayout as ViewGroup?
                ) as LinearLayout
            }
            MaxElementMother.PALMIER -> {
                recipeLinearLayout = View.inflate(
                    context,
                    R.layout.recipe_palmier,
                    recipeLinearLayout as ViewGroup?
                ) as LinearLayout
            }
        }
    }

    private fun goToUrlinNewIntent(context: Context, url: String) {
        val uri = Uri.parse(url)
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun backButtonMethod() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    // ANDROID CELLPHONE NATIVE OPTION PART :
    override fun onBackPressed() {
        backButtonMethod()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}