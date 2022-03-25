package fr.mm.lemementoboulangerie_0_1_0.bibliotheques

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import java.io.*
import java.lang.StringBuilder
import java.time.Clock
import java.util.*
import kotlin.math.pow

abstract class MaximeToolsLayoutV1 {
    fun timeInterfaceThreadToUpdateActivityViews(
        activity: AppCompatActivity,
        delayInMs: Int,
        itf: timerInterface
    ) {
        val th = Thread(object : Runnable {
            private val startTime = System.currentTimeMillis() // time in ms since 1970
            override fun run() {
                while (true) {
                    activity.runOnUiThread { itf.method() }
                    try {
                        Thread.sleep(delayInMs.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        })
        th.start()
    }

    interface storageInterface {
        fun whenStorageisGranded()
    }

    // FILES METHODS :
    /*
    "https://api.coinbase.com/v2/prices/BTC-USD/spot" @link
    */
    interface downloadFileInterface {
        fun toDoWhenDownloaded()
    }

    interface timerInterface {
        fun method()
    }

    companion object {
        const val TAG = "MAXTAG"
        val downloadDirPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        var broadcastReceiver: BroadcastReceiver? = null
        var fileDownload: File? = null

        // ---> LinearLayout.LayoutParams :
        fun setLayoutParams(
            context: Context,
            layout_width: Int,
            layout_height: Int,
            margin_left: Int,
            margin_right: Int,
            margin_top: Int,
            margin_bottom: Int,
            gravity: Int,
            weight: Float
        ): LinearLayout.LayoutParams {
            val params = LinearLayout.LayoutParams(layout_width, layout_height)
            params.setMargins(
                convertDpToPixel(context, margin_left),
                convertDpToPixel(context, margin_top),
                convertDpToPixel(context, margin_right),
                convertDpToPixel(context, margin_bottom)
            )
            params.gravity = gravity
            params.weight = weight
            return params
        }

        // ---> LINEARLAYOUT :
        fun setLinearLayout(
            context: Context?,
            maximeToolsLayoutParams: LinearLayout.LayoutParams?,
            linearLayout_Orientation: Int
        ): LinearLayout {
            val linearLayout = LinearLayout(context)
            linearLayout.layoutParams = maximeToolsLayoutParams
            linearLayout.orientation = linearLayout_Orientation
            return linearLayout
        }

        // ---> TEXTVIEW :
        fun setTextView(context: Context, text: String?): TextView {
            val textView = TextView(context)
            textView.layoutParams = setLayoutParams(
                context,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0,
                0,
                0,
                0,
                0,
                0f
            )
            textView.text = text
            return textView
        }

        fun setTextView(
            context: Context?,
            layoutParams: LinearLayout.LayoutParams?,
            text: String?
        ): TextView {
            val textView = TextView(context)
            textView.layoutParams = layoutParams
            textView.text = text
            return textView
        }

        // ---> LINEARLAYOUT WITH TWO TEXTVIEW :
        fun setLinearLayoutWithTwoTextView(
            context: Context,
            text1: String?,
            text2: String?
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5,
                    5,
                    5,
                    5,
                    0,
                    0f
                ), LinearLayout.HORIZONTAL
            )
            var textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    10,
                    0,
                    0,
                    Gravity.CENTER,
                    1f
                ),
                text1
            )
            textView.gravity = Gravity.END
            layout.addView(textView)
            textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    10,
                    0,
                    0,
                    0,
                    Gravity.CENTER,
                    1f
                ),
                text2
            )
            textView.gravity = Gravity.START
            layout.addView(textView)
            return layout
        }

        fun setLinearLayoutWithTwoTextView(
            context: Context,
            text1: String?,
            weight1: Int,
            text2: String?,
            weight2: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5,
                    5,
                    5,
                    5,
                    0,
                    0f
                ), LinearLayout.HORIZONTAL
            )

            // TextView1 :
            var textView = setTextView(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 10, 0, 0, Gravity.CENTER, weight1.toFloat()
                ),
                text1
            )
            textView.gravity = Gravity.END
            layout.addView(textView)

            // TextView2 :
            textView = setTextView(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    10, 0, 0, 0, Gravity.CENTER, weight2.toFloat()
                ),
                text2
            )
            textView.gravity = Gravity.START
            layout.addView(textView)
            return layout
        }

        fun setLinearLayoutWithTwoTextView(
            context: Context,
            text1: String?,
            gravity1: Int,
            size1: Int,
            intColor1: Int,
            weight1: Int,
            text2: String?,
            gravity2: Int,
            size2: Int,
            intColor2: Int,
            weight2: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    5,
                    5,
                    5,
                    5,
                    0,
                    0f
                ), LinearLayout.HORIZONTAL
            )

            // TextView1 :
            var textView = setTextView(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 10, 0, 0, Gravity.CENTER, weight1.toFloat()
                ),
                text1
            )
            textView.setTextColor(context.getColor(intColor1))
            textView.textSize = size1.toFloat()
            textView.gravity = gravity1
            layout.addView(textView)

            // TextView2 :
            textView = setTextView(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    10, 0, 0, 0, Gravity.CENTER, weight2.toFloat()
                ),
                text2
            )
            textView.setTextColor(context.getColor(intColor2))
            textView.textSize = size2.toFloat()
            textView.gravity = gravity2
            layout.addView(textView)
            return layout
        }

        // ---> LINEARLAYOUT WITH IMAGEVIEW AND TEXTVIEW :
        fun setLinearLayoutWithImageViewAndTextView(
            context: Context,
            rIntDrawableImage1: Int,
            weight1: Int,
            text2: String?,
            color: Int,
            weight2: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0f
                ), LinearLayout.HORIZONTAL
            )
            val imageView = ImageView(context)
            imageView.layoutParams = setLayoutParams(
                context, 0, 100,
                0, 0, 0, 0, Gravity.CENTER, weight1.toFloat()
            )
            imageView.setImageDrawable(ContextCompat.getDrawable(context, rIntDrawableImage1))
            layout.addView(imageView)
            val textView = setTextView(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.MATCH_PARENT + 100,
                    0, 0, 0, 0, Gravity.CENTER, weight2.toFloat()
                ),
                text2
            )
            textView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            textView.textSize = 18f
            textView.setTextColor(context.getColor(color))
            layout.addView(textView)
            return layout
        }

        fun setLinearLayoutWithImageViewAndMultiTextView(
            context: Context,
            rIntDrawableImage1: Int,
            weightImgPerCent: Int,
            textarray: Array<String?>,
            colorID: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    5,
                    5,
                    Gravity.CENTER,
                    0f
                ), LinearLayout.HORIZONTAL
            )
            val imageView = ImageView(context)
            imageView.layoutParams = setLayoutParams(
                context, 0, 200,
                0, 0, 0, 0, Gravity.CENTER, weightImgPerCent.toFloat()
            )
            imageView.setImageDrawable(ContextCompat.getDrawable(context, rIntDrawableImage1))
            layout.addView(imageView)
            val textLinearLayout = setLinearLayout(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 0, 0, 0, Gravity.CENTER, 100f
                ), LinearLayout.VERTICAL
            )
            for (i in textarray.indices) {
                val textView = setTextView(
                    context,
                    setLayoutParams(
                        context, LinearLayout.LayoutParams.MATCH_PARENT, 0,
                        0, 0, 0, 0, Gravity.CENTER, 1f
                    ),
                    textarray[i]
                )
                textView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                textView.setTextColor(context.getColor(colorID))
                textLinearLayout.addView(textView)
            }
            layout.addView(textLinearLayout)
            return layout
        }

        // ---> LINEARLAYOUT WITH IMAGEVIEWUp AND TEXTVIEWBelow :
        fun setLinearLayoutWithImageViewUPAndTextViewBELOW(
            context: Context,
            rIntDrawableImage1: Int,
            text: String?,
            color: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 0, 0, 0, Gravity.CENTER, 1f
                ), LinearLayout.VERTICAL
            )
            val imageView = ImageView(context)
            imageView.layoutParams = setLayoutParams(
                context, LinearLayout.LayoutParams.WRAP_CONTENT, 100,
                0, 0, 0, 0, Gravity.CENTER, 0f
            )
            imageView.setImageDrawable(ContextCompat.getDrawable(context, rIntDrawableImage1))
            layout.addView(imageView)
            val textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    0,
                    0,
                    Gravity.CENTER,
                    0f
                ),
                text
            )
            textView.gravity = Gravity.CENTER
            textView.textSize = 18f
            textView.setTextColor(context.getColor(color))
            layout.addView(textView)
            return layout
        }

        // RESOURCE DISPLAY :
        fun setLinearLayoutWithImageViewUPAndTextViewBELOWRESSOURCESDISPLAY(
            context: Context,
            rIntDrawableImage1: Int,
            text: String?,
            color: Int,
            text2: String?
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context, 0, LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 0, 0, 0, Gravity.CENTER, 1f
                ), LinearLayout.VERTICAL
            )
            val imageView = ImageView(context)
            imageView.layoutParams = setLayoutParams(
                context, LinearLayout.LayoutParams.WRAP_CONTENT, 100,
                0, 0, 0, 0, Gravity.CENTER, 0f
            )
            imageView.setImageDrawable(ContextCompat.getDrawable(context, rIntDrawableImage1))
            layout.addView(imageView)
            var textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    0,
                    0,
                    Gravity.CENTER,
                    0f
                ),
                text
            )
            textView.gravity = Gravity.CENTER
            textView.textSize = 18f
            textView.setTextColor(context.getColor(color))
            layout.addView(textView)
            textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    0,
                    0,
                    Gravity.CENTER,
                    0f
                ),
                text2
            )
            textView.gravity = Gravity.CENTER
            textView.textSize = 12f
            textView.setTextColor(context.getColor(color))
            layout.addView(textView)
            return layout
        }

        // PLANET DISPLAY :
        fun setLinearLayoutWithImageViewUPAndTextViewBELOWVERTICAL(
            context: Context,
            rIntDrawableImage1: Int,
            text: String?,
            color: Int
        ): LinearLayout {
            val layout = setLinearLayout(
                context,
                setLayoutParams(
                    context, LinearLayout.LayoutParams.MATCH_PARENT, 0,
                    0, 0, 20, 20, Gravity.CENTER, 1f
                ), LinearLayout.VERTICAL
            )
            val imageView = ImageView(context)
            imageView.layoutParams = setLayoutParams(
                context, 150, 150,
                0, 0, 0, 0, Gravity.CENTER, 0f
            )
            imageView.setImageDrawable(ContextCompat.getDrawable(context, rIntDrawableImage1))
            layout.addView(imageView)
            val textView = setTextView(
                context,
                setLayoutParams(
                    context,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0,
                    0,
                    0,
                    0,
                    Gravity.CENTER,
                    0f
                ),
                text
            )
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(context.getColor(color))
            layout.addView(textView)
            return layout
        }

        // EDIT TEXT VIEW :
        fun hideKeyboard(context: Context, v: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }

        fun showKeyboard(context: Context, v: View?) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
        }

        // IMAGE :
        fun setImageBitmap(context: Context, rDrawablePathImage: Int): Bitmap {
            return BitmapFactory.decodeResource(context.resources, rDrawablePathImage)
        }

        // ANIMATION :
        fun runAnimationTextIN(
            context: Context?,
            rAnimPath: Int,
            textView: TextView,
            text: String?
        ) {
            val a = AnimationUtils.loadAnimation(context, rAnimPath)
            a.reset()
            textView.clearAnimation()
            textView.text = text
            textView.startAnimation(a)
        }

        fun runAnimationTextOUT(context: Context?, rAnimPath: Int, textView: TextView) {
            val a = AnimationUtils.loadAnimation(context, rAnimPath)
            a.reset()
            textView.clearAnimation()
            textView.startAnimation(a)
        }

        // MUSIC and SOUND :
        fun setMusicMediaPlayer(context: Context?, rRawPathMusicSound: Int): MediaPlayer {
            var mediaPlayer: MediaPlayer
            mediaPlayer = MediaPlayer.create(context, rRawPathMusicSound)
            return mediaPlayer
        }

        fun musicMediaPlayerPlay(mediaPlayer: MediaPlayer?, isLooping: Boolean?) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(1)
                } else {
                    mediaPlayer.start()
                }
                mediaPlayer.isLooping = isLooping!!
            }
        }

        fun musicMediaPlayerPlay(context: Context?, rRawPath: Int, isLooping: Boolean?) {
            val mediaPlayer: MediaPlayer
            mediaPlayer = MediaPlayer.create(context, rRawPath)
            mediaPlayer.isLooping = isLooping!!
            mediaPlayer.seekTo(1)
            mediaPlayer.start()
        }

        fun musicMediaPlayerStopResetRelease(mediaPlayer: MediaPlayer?) {
            if (mediaPlayer != null) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        }

        fun musicMediaPlayerChangeMusicAndPlay(
            context: Context?,
            mediaPlayer: MediaPlayer?,
            rRawPath: Int,
            isLooping: Boolean?
        ): MediaPlayer? {
            var medplay = mediaPlayer
            if (medplay != null) {
                medplay.stop()
                medplay.reset()
                medplay.release()
            }
            medplay = MediaPlayer.create(context, rRawPath)
            medplay.seekTo(1)
            medplay.start()
            medplay.isLooping = isLooping!!
            return medplay
        }

        //FILES AND PERMISSION REQUEST :
        const val get_CAMERA_PERMISSION_CODE = 100
        const val get_STORAGE_PERMISSION_CODE = 101

        /**
         *
         * **EXEMPLE UTILISATION :**
         *
         *
         * A utiliser dans la fonction  :
         *
         *
         * Override
         *
         *
         * public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
         *
         *
         * {
         *
         *
         * super.onRequestPermissionsResult(requestCode,permissions,grantResults);
         *
         *
         * VOTRE METHOD permissionRequestStorage(Context, int, int[], storageInterface)
         *
         *
         * }
         *
         *
         * @param context          the ContextClass where is called "onRequestPermissionsResult()".
         * @param requestCode      The request code passed in "onRequestPermissionsResult()".
         * @param storageInterface the interface with single Method() to do when request is granted.
         * @param grantResults     The grant results passed in "onRequestPermissionsResult()".
         */
        fun permissionRequestStorage(
            context: Context?,
            requestCode: Int,
            grantResults: IntArray,
            storageInterface: storageInterface
        ) {

            /*
        MAX EXEMPLE :

        @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        MaximeToolsLayoutV1.permissionRequestStorage(context, requestCode, grantResults, new MaximeToolsLayoutV1.storageInterface() {
            @Override
            public void whenStorageisGranded() {

                # add code here
            }
        });
    }
         */
            if (requestCode == get_CAMERA_PERMISSION_CODE) {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        context,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else if (requestCode == get_STORAGE_PERMISSION_CODE) {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        context,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    storageInterface.whenStorageisGranded()
                } else {
                    Toast.makeText(
                        context,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        fun permissionCHECK(
            context: Context?,
            permission: String,
            requestCode: Int,
            storageInterface: storageInterface
        ) {
            if (ContextCompat.checkSelfPermission(context!!, permission)
                == PackageManager.PERMISSION_DENIED
            ) {

                // Requesting the permission
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!, arrayOf(permission),
                    requestCode
                )
            } else {
                Toast.makeText(
                    context,
                    "Permission already granted", Toast.LENGTH_SHORT
                ).show()
                storageInterface.whenStorageisGranded()
            }
        }

        fun downloadInContentFilesDir(
            context: Context,
            urlPath: String?,
            fileWithExtension: String,
            downloadFileInterface: downloadFileInterface
        ) {
            var filetest =
                fileCreateOverRideMethods("/storage/self/primary" + context.filesDir.path + "/" + fileWithExtension)
            filetest?.delete()
            filetest =
                fileCreateOverRideMethods("/storage/self/primary" + context.filesDir.path + "/" + fileWithExtension + "-1")
            filetest?.delete()
            filetest =
                fileCreateOverRideMethods("/storage/self/primary" + context.filesDir.path + "/" + fileWithExtension + "-2")
            filetest?.delete()
            filetest =
                fileCreateOverRideMethods("/storage/self/primary" + context.filesDir.path + "/" + fileWithExtension + "-3")
            filetest?.delete()
            val uri = Uri.parse(urlPath)
            val downloadmanager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(uri)
            //        request.setTitle("dlFile.json");
            request.setDescription("Downloading file")
            request.setAllowedOverMetered(true)
            request.setAllowedOverRoaming(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setMimeType(getMimeType(context, uri))
            request.setDestinationInExternalPublicDir(
                context.filesDir.path,
                "/$fileWithExtension"
            )
            //        request.setDestinationUri(Uri.fromFile(new File(context.getFilesDir().getPath(),"PriceCoin1.json")));
            val myDownloadID = downloadmanager.enqueue(request)

            // MESSAGE CONFIRMATION DownLoad :
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == myDownloadID) {
                        fileDownload =
                            fileCreateOverRideMethods("/storage/self/primary" + context.filesDir.path + "/" + fileWithExtension)
                        Toast.makeText(context, "DownLoad COMPLETE", Toast.LENGTH_SHORT).show()
                        downloadFileInterface.toDoWhenDownloaded()

//                    Sauvegarde sauvegarde = MaximeToolsLayoutV1.deserializeSauvegardeFromFILE(context, MaximeToolsLayoutV1.fileDownload);
//
//                    assert sauvegarde != null;
//                    Data data = sauvegarde.data;
//                    String text = data.getInfoDataToString();
//                    if (textViewTest != null) {
//                        textViewTest.setText(text);
//                    }
//
//                    if(MaximeToolsLayoutV1.fileDownload.delete()){
//                        MaximeToolsLayoutV1.fileDownload = null;
//                    }
                    }
                }
            }
            context.registerReceiver(
                broadcastReceiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }

        private fun getMimeType(context: Context, uri: Uri): String? {
            val resolver = context.contentResolver
            val mimeTypeMap = MimeTypeMap.getSingleton()
            return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri))
        }

        fun fileCreateOverRideMethods(pathOfFile: String?): File? {
            val myFile: File


//        File myFile = new File("/data/data/fr.maximeproduction.boursemaxapp/files/" + nameOfFIleWithExtension); same as below
            myFile = File(pathOfFile!!)
            if (myFile.exists()) {
                Log.i(TAG, myFile.name + " already exist.")
                Log.i(TAG, myFile.path)
                Log.i(TAG, myFile.length().toString() + " Bytes")
                return myFile
            } else {
                Log.i(TAG, myFile.name + " don't exist.")
            }
            fIleINFOLITEMethods(myFile)
            return null
        }

        fun fileCreateMethods(pathOfFile: String?): File? {
            var myFile: File? = null
            try {

//        File myFile = new File("/data/data/fr.maximeproduction.boursemaxapp/files/" + nameOfFIleWithExtension); same as below
                myFile = File(pathOfFile!!)
                if (myFile.createNewFile()) {
                    Log.i(TAG, myFile.name + " created.")
                } else {
                    Log.i(TAG, myFile.name + " already exist.")
                }
                fIleINFOLITEMethods(myFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return myFile
        }

        fun fileCreateMethodsInFilesDirectory(
            context: Context,
            nameOfFileWithExtension: String
        ): File? {
            var myFile: File? = null
            try {

//        File myFile = new File("/data/data/fr.maximeproduction.boursemaxapp/files/" + nameOfFIleWithExtension); same as below
                myFile = File(context.filesDir.path + "/" + nameOfFileWithExtension)
                if (myFile.createNewFile()) {
                    Log.i(TAG, myFile.name + " created.")
                } else {
                    Log.i(TAG, myFile.name + " already exist.")
                }
                fIleINFOLITEMethods(myFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return myFile
        }

        fun fileWriteMethod(fileInToWrite: File?, textToWrite: String) {
            try {
                val myWriter = FileWriter(fileInToWrite!!.path)
                myWriter.write(textToWrite)
                myWriter.close()
                Log.i(
                    TAG,
                    "Successfully wrote <" + textToWrite + "> to the file : " + fileInToWrite.name
                )
            } catch (e: IOException) {
                Log.i(TAG, "ERROR to write to the file : " + fileInToWrite!!.name)
                e.printStackTrace()
            }
        }

        fun fileReadMethod(fileToRead: File): String {
            val dataRead = StringBuilder()
            try {
                val myReader = Scanner(fileToRead)
                while (myReader.hasNextLine()) {
                    dataRead.append(myReader.nextLine())
                }
                myReader.close()
            } catch (e: FileNotFoundException) {
                Log.i(TAG, "ERROR to read the file : " + fileToRead.name)
                e.printStackTrace()
            }
            return dataRead.toString()
        }

        fun filesMoving(fileSource: File, fileTarget: File) {
            Log.i(TAG, "\n\n\t --- MOVING file : --- ")
            Log.i(
                TAG,
                " Source move file : " + fileSource.name + " WritePermission : " + fileSource.canWrite()
            )
            Log.i(
                TAG,
                " Source move file : " + fileSource.name + " ReadPermission : " + fileSource.canRead()
            )
            Log.i(
                TAG,
                " Target move file : " + fileTarget.name + " WritePermission : " + fileTarget.canWrite()
            )
            Log.i(
                TAG,
                " Target move file : " + fileTarget.name + " ReadPermission : " + fileTarget.canRead()
            )
            if (fileTarget.renameTo(fileSource)) {
                Log.i(
                    TAG,
                    " Succeed moving files :) "
                )
            } else {
                Log.i(
                    TAG,
                    " Failed moving files :( "
                )
            }
        }

        private fun fIleINFOLITEMethods(file: File) {
            if (file.exists()) {
                Log.i(
                    TAG,
                    """ 

	--- INFO File name : ${file.parent}/ < ${file.name} > ---"""
                )
                try {
                    Log.i(TAG, file.name + " canonicalPath : " + file.canonicalPath)
                } catch (e: IOException) {
                    Log.i(TAG, "exception canonicalPath : " + e.message)
                }
                Log.i(TAG, file.name + " canRead        : " + file.canRead())
                Log.i(TAG, file.name + " canWrite       : " + file.canWrite())
                Log.i(
                    TAG,
                    """${file.name} size           : ${file.length()} Bytes
end"""
                )
            } else {
                Log.i(TAG, " File doesn't exist ")
            }
        }

        private fun fIleINFOMethods(file: File) {
            if (file.exists()) {
                Log.i(TAG, " --- File name : " + file.name + " ---")
                Log.i(TAG, file.name + " parent path   : " + file.parent)
                Log.i(TAG, file.name + " absolute path : " + file.absolutePath)
                Log.i(TAG, file.name + " path          : " + file.path)
                try {
                    Log.i(TAG, file.name + " canonicalPath : " + file.canonicalPath)
                } catch (e: IOException) {
                    Log.i(TAG, "exception canonicalPath : " + e.message)
                }
                Log.i(TAG, file.name + " canRead        : " + file.canRead())
                Log.i(TAG, file.name + " canWrite       : " + file.canWrite())
                Log.i(TAG, file.name + " size           : " + file.length() + " Bytes")
                Log.i(TAG, file.name + " canExecute     : " + file.canExecute())
                Log.i(TAG, file.name + " isAbsolute     : " + file.isAbsolute)
                Log.i(TAG, file.name + " isDirectory    : " + file.isDirectory)
                Log.i(TAG, file.name + " isFile         : " + file.isFile)
                Log.i(TAG, file.name + " isHidden       : " + file.isHidden)
                Log.i(TAG, file.name + " getFreeSpace   : " + file.freeSpace)
                Log.i(TAG, file.name + " getTotalSpace  : " + file.totalSpace)
                Log.i(TAG, file.name + " getUsableSpace : " + file.usableSpace)
            } else {
                Log.i(TAG, " File doesn't exist ")
            }
        }

        //SERIALIZATION GSON :
        fun serializeSauvegardeFromFILE(file: File?, sauvegarde: Sauvegarde?) {

            // POUR G-SON SERIALIZER : Add in build.gradle(:app) ou (Module: xxx)
            // dependencies {
            //    implementation 'com.google.code.gson:gson:2.8.2'
            val gson = Gson()
            val gsonString = gson.toJson(sauvegarde)
            val fileOutputStream: FileOutputStream
            try {
//            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(gsonString.toByteArray())
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun deserializeSauvegardeFromFILE(fileToDeserialize: File?): Sauvegarde? {

            // POUR G-SON SERIALIZER : Add in build.gradle(Module: xxx)
            // dependencies {
            //    implementation 'com.google.code.gson:gson:2.8.2'

            // POUR G-SON SERIALIZER : Add in build.gradle(Module: xxx)
            // dependencies {
            //    implementation 'com.google.code.gson:gson:2.8.2'
            var fis: FileInputStream?
            return try {
                if (fileToDeserialize != null) {
                    fis = FileInputStream(fileToDeserialize)
                    val isr = InputStreamReader(fis)
                    val bufferedReader = BufferedReader(isr)
                    val sb = StringBuilder()
                    var line: String? = null
                    while (true) {
                        try {
                            if (bufferedReader.readLine().also { line = it } == null) break
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        sb.append(line)
                    }
                    val json = sb.toString()
                    val gson = Gson()
                    gson.fromJson(json, Sauvegarde::class.java)
                } else {
                    null
                }
            } catch (fileNotFoundException: FileNotFoundException) {
                fileNotFoundException.printStackTrace()
                null
            }
        }

        fun serializeSauvegardeToAppFileDirectory(
            context: Context,
            filename: String?,
            sauvegarde: Sauvegarde?
        ) {

            // Add implementation 'com.google.code.gson:gson:2.8.2' in build.gradle(Module:'nom de l'Application'.app) dependencies :

            /*  POUR G-SON SERIALIZER :

        In Gradle Scripts / build.gradle(Module:'nom de l'Application'.app) or write like that in upper tab : build.gradle(:app)

                Add implementation 'com.google.code.gson:gson:2.8.2' in dependencies,

                    dependencies {

                          implementation 'com.google.code.gson:gson:2.8.2'

                                  } M@x1me.M0y4 ;) */
            val gson = Gson()
            val gsonString = gson.toJson(sauvegarde)
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
                fileOutputStream.write(gsonString.toByteArray())
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun deserializeSauvegardeFromAppFileDirectory(
            context: Context,
            filename: String?
        ): Sauvegarde? {

            // POUR G-SON SERIALIZER : Add in build.gradle(Module: xxx)
            // dependencies {
            //    implementation 'com.google.code.gson:gson:2.8.2'
            val fis: FileInputStream
            return try {
                fis = context.openFileInput(filename)
                val isr = InputStreamReader(fis)
                val bufferedReader = BufferedReader(isr)
                val sb = StringBuilder()
                var line: String? = null
                while (true) {
                    try {
                        if (bufferedReader.readLine().also { line = it } == null) break
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    sb.append(line)
                }
                val json = sb.toString()
                val gson = Gson()
                gson.fromJson(json, Sauvegarde::class.java)
            } catch (fileNotFoundException: FileNotFoundException) {
                fileNotFoundException.printStackTrace()
                null
            }
        }

        // My METHODS FORMAT numbers TO STRING :
        fun getNumberToStringFormatUnitKiloMegaGigaWithOneDecimal(d: Double): String {
            val stringToReturn: String
            stringToReturn = if (d > 999999999) {
                val dd = Math.floor(d / 100000000) / 10
                "$dd G"
            } else if (d > 999999) {
                val dd = Math.floor(d / 100000) / 10
                "$dd M"
            } else if (d > 999) {
                val dd = Math.floor(d / 100) / 10
                "$dd k"
            } else if (d >= 0) {
                val dd = Math.floor(d * 10) / 10
                "$dd u"
            } else {
                val dd = Math.floor(d * 10) / 10
                "$dd u"
            }
            return stringToReturn
        }

        fun getNumberToStringFormatUnitKiloMegaGigaWithOneDecimal(
            d: Double,
            Unite: String,
            Kilo: String,
            Mega: String,
            Giga: String
        ): String {
            val stringToReturn: String
            stringToReturn = if (d > 999999999) {
                val dd = Math.floor(d / 100000000) / 10
                "$dd $Giga"
            } else if (d > 999999) {
                val dd = Math.floor(d / 100000) / 10
                "$dd $Mega"
            } else if (d > 999) {
                val dd = Math.floor(d / 100) / 10
                "$dd $Kilo"
            } else if (d >= 0) {
                val dd = Math.floor(d * 10) / 10
                "$dd $Unite"
            } else {
                val dd = Math.floor(d * 10) / 10
                "$dd $Unite"
            }
            return stringToReturn
        }

        fun getNumberToStringFormatDayHourMinSecond(numberInSeconds: Int): String {
            val stringToReturn: String
            val jourString = " d "
            val heureString = " h "
            val minuteString = " m "
            val secondeString = " s"
            stringToReturn = if (numberInSeconds < 60) {
                "$numberInSeconds s"
            } else if (numberInSeconds < 3600) {
                val minutes = numberInSeconds / 60
                val secondes = numberInSeconds % 60
                "$minutes m $secondes s"
            } else if (numberInSeconds < 24 * 3600) {
                val heures = numberInSeconds / 3600
                val resteHeures = numberInSeconds % 3600
                val minutes = resteHeures / 60
                val secondes = resteHeures % 60
                "$heures h $minutes m $secondes s"
            } else {
                val jour = numberInSeconds / (24 * 3600)
                val resteJour = numberInSeconds % (24 * 3600)
                val heures = resteJour / 3600
                val resteHeures = resteJour % 3600
                val minutes = resteHeures / 60
                val secondes = resteHeures % 60
                "$jour d $heures h $minutes m $secondes s"
            }
            return stringToReturn
        }

        // My METHODS STRING TEXTE LOG :
        fun printLogInfo(texteToPrintInConsole: String?) {
            Log.i(
                TAG,
                texteToPrintInConsole!!
            )
        }

        // My METHODS Handler and Thread timer :
        private val handler = Handler()
        private var runnable: Runnable? = null
        fun startTimerMethod(delayMilisec: Short, timerInterface: Array<timerInterface>) {
            runnable = object : Runnable {
                override fun run() {
                    val instantTimeStart =
                        Clock.systemUTC().instant().toEpochMilli() // ms since 1970
                    //                Log.i(TAG, " START : " + instantTimeStart + " ms");
                    for (i in timerInterface.indices) {
                        timerInterface[i].method()
                    }
                    val instantTimeEnd = Clock.systemUTC().instant().toEpochMilli() // ms since 1970
                    //                Log.i(TAG, " END interfaces : " + instantTimeEnd + " ms");
                    val timeDelay = instantTimeEnd - instantTimeStart // ms delayed
                    //                Log.i(TAG, " Delay : " + timeDelay + " ms");
                    if (delayMilisec - timeDelay > 0) {
                        handler.postDelayed(this, delayMilisec - timeDelay)
                        //                    Log.i(TAG, " handler postDelayed : " + (delayMilisec - timeDelay) + " ms");
                    } else {
                        handler.postDelayed(this, 1)
                        //                    Log.i(TAG, " handler postDelayed : 1 ms");
                    }
                }
            }
            (runnable as Runnable).run()
        }

        fun startTimerMethod(delayMilisec: Int, timerInterface: Array<timerInterface>) {
            runnable = object : Runnable {
                override fun run() {
                    val instantTimeStart =
                        Clock.systemUTC().instant().toEpochMilli() // ms since 1970
                    //                Log.i(TAG, " START : " + instantTimeStart + " ms");
                    for (i in timerInterface.indices) {
                        timerInterface[i].method()
                    }
                    val instantTimeEnd = Clock.systemUTC().instant().toEpochMilli() // ms since 1970
                    //                Log.i(TAG, " END interfaces : " + instantTimeEnd + " ms");
                    val timeDelay = instantTimeEnd - instantTimeStart // ms delayed
                    //                Log.i(TAG, " Delay : " + timeDelay + " ms");
                    if (delayMilisec - timeDelay > 0) {
                        handler.postDelayed(this, delayMilisec - timeDelay)
                        //                    Log.i(TAG, " handler postDelayed : " + (delayMilisec - timeDelay) + " ms");
                    } else {
                        handler.postDelayed(this, 1)
                        //                    Log.i(TAG, " handler postDelayed : 1 ms");
                    }
                }
            }
            (runnable as Runnable).run()
        }

        fun stopTimerMethod() {
            handler.removeCallbacks(
                runnable!!
            )
        }

        // MATH :
        fun roundNumber(dbl: Double, decimal: Int): Double {
            return Math.round(dbl * Math.pow(10.0, decimal.toDouble())) / Math.pow(
                10.0,
                decimal.toDouble()
            )
        }

        fun truncateNumber(dbl: Double, decimal: Int): Double {
            return Math.floor(dbl * Math.pow(10.0, decimal.toDouble())) / Math.pow(
                10.0,
                decimal.toDouble()
            )
        }

        private fun convertDpToPixel(context: Context, yourDpMeasure: Int): Int {
            val r = context.resources
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                yourDpMeasure.toFloat(),
                r.displayMetrics
            ).toInt()
        }

        // NETWORK :
        fun isNetworkSystemOn(context: Context): Boolean {
            // NEED THIS PERMISSION IN MANIFEST :
            //    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
            var have_wifi = false
            var have_MobileData = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfos = connectivityManager.allNetworkInfo
            for (info in networkInfos) {
                if (info.typeName.equals("WIFI", ignoreCase = true)) {
                    if (info.isConnected) {
                        have_wifi = true
                    }
                }
                if (info.typeName.equals("MOBILE", ignoreCase = true)) {
                    if (info.isConnected) {
                        have_MobileData = true
                    }
                }
            }
            return have_MobileData || have_wifi
        }

        // DATE :
        fun isAutomaticDateTimeSystemON(context: Context): Boolean {
            return if (Settings.Global.getInt(
                    context.contentResolver,
                    Settings.Global.AUTO_TIME,
                    0
                ) == 1
            ) {
                true
            } else {
                false
            }
        }// peut etre utile a connaitre :
        /*
           Date date = new Date();
           int timeNow = date.getHours();
           Calendar calendrier = Calendar.getInstance(TimeZone.getDefault());
           */
        /**
         *
         * **DATE CALENDAIRE ACTUELLE :**
         *
         *
         * Fonction utilisant la classe Calendar() pour lister : **jour** / **mois** / **annee**
         *
         * @return List'int'(Jour/Mois/Annee)
         * @author Maxime Moya
         * @see Calendar
         */
        val dateDayMonthYear: List<Int>
            get() {
                val listToReturn: MutableList<Int> = ArrayList()

                // peut etre utile a connaitre :
                /*
                   Date date = new Date();
                   int timeNow = date.getHours();
                   Calendar calendrier = Calendar.getInstance(TimeZone.getDefault());
                   */
                val dateDay = Calendar.getInstance()[Calendar.DATE]
                val dateMonth = Calendar.getInstance()[Calendar.MONTH] + 1
                val dateYear = Calendar.getInstance()[Calendar.YEAR]
                listToReturn.add(dateDay)
                listToReturn.add(dateMonth)
                listToReturn.add(dateYear)
                return listToReturn
            }

        /**
         *
         * **DATE CALENDAIRE ACTUELLE :**
         *
         *
         * Fonction utilisant la classe Calendar() pour lister : **jour** / **mois** / **annee**
         *
         * @return List'int'(Jour/Mois/Annee)
         * @author Maxime Moya
         * @see Calendar
         */
        fun getDateDayMonthYear(dateInMs: Long): List<Int> {
            val listToReturn: MutableList<Int> = ArrayList()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateInMs
            val dateDay = calendar[Calendar.DATE]
            val dateMonth = calendar[Calendar.MONTH] + 1
            val dateYear = calendar[Calendar.YEAR]
            listToReturn.add(dateDay)
            listToReturn.add(dateMonth)
            listToReturn.add(dateYear)
            return listToReturn
        }

        /**
         *
         * **COMPARE DATE BY DAYS :**
         *
         *
         * Fonction retournant une comparaison par jour :
         *
         * **-1** si dateA AVANT "<" dateB
         *
         * **0** si dateA = dateB
         *
         * **1** si dateA APRES ">" dateB
         *
         * @return int => ( -1 / 0 / 1 )
         * @author Maxime Moya
         * @see Calendar
         */
        fun getDateDayComparator(dateInMsA: Long, dateInMsB: Long): Int {
            val dateA = getDateDayMonthYear(dateInMsA)
            val dateB = getDateDayMonthYear(dateInMsB)

            // Annees identiques :
            return if (dateA[2] == dateB[2]) {

                // Mois identiques :
                if (dateA[1] == dateB[1]) {
                    if (dateA[0] < dateB[0]) {
                        -1
                    } else if (dateA[0] == dateB[0]) {
                        0
                    } else {
                        1
                    }
                } else if (dateA[1] < dateB[1]) {
                    -1
                } else {
                    1
                }
            } else if (dateA[2] < dateB[2]) {
                -1
            } else {
                1
            }
        }

        /**
         *
         * **COMPARE DATE BY DAYS INTERVAL In Same Month :**
         *
         *
         * Fonction retournant une comparaison par jour selon interval :
         *
         * **true** si dateA AVANT "<" dateB de 'dayInterval' days include.
         *
         *  exemple si interval = 0, pour savoir si dateA et B sont le meme jour /
         *
         *  interval = i pour i jours de decalage...
         *
         * **false** sinon.
         *
         * @return boolean => ( true / false )
         * @author Maxime Moya
         * @see Calendar
         */
        fun getDateDaySameMonthComparator(
            dateInMsA: Long,
            dateInMsB: Long,
            dayInterval: Int
        ): Boolean {
            val dateA = getDateDayMonthYear(dateInMsA)
            val dateB = getDateDayMonthYear(dateInMsB)
            val resultInterval = dateB[0] - dateA[0]

            // Annees identiques :
            if (dateA[2] == dateB[2]) {

                // Mois identiques :
                if (dateA[1] == dateB[1]) {
                    if (resultInterval == dayInterval) {
                        return true
                    }
                }
            }
            return false
        }

        // CREDIT :
        fun codeTextToMaxCyFileV0(
            file: File?,
            listKV: List<HashMap<String?, String?>>,
            text: String
        ) {
            val stringBuilder = StringBuilder()
            var c: Char
            for (i in 0 until text.length) {
                c = text[i]
                stringBuilder.append(listKV[0][c.toString()])
            }
            fileWriteMethod(file, stringBuilder.toString())
        }

        fun decodeMaxCyFileToTextV0(
            fileToDecode: File,
            listKV: List<HashMap<String?, String>>,
            fileToStore: File?
        ) {
            val rawText = fileReadMethod(fileToDecode)
            val stringBuilder = StringBuilder()
            var c: Char
            var i = 0
            while (i < rawText.length - 2) {
                val stringBuilderRaw = StringBuilder()
                c = rawText[i]
                stringBuilderRaw.append(c)
                c = rawText[i + 1]
                stringBuilderRaw.append(c)
                c = rawText[i + 2]
                stringBuilderRaw.append(c)
                val decText = stringBuilderRaw.toString()
                for (c2 in 0..127) {
                    if (decText == listKV[0][c2.toString()]) {
                        stringBuilder.append(c2)
                        break
                    }
                }
                i += 3
            }
            fileWriteMethod(fileToStore, stringBuilder.toString())
        }

        fun dicoKVGen(quantityListToReturn: Int): List<HashMap<String, String?>> {
            var c = 0.toChar()
            val listeLettre: MutableList<String> = ArrayList()
            while (c.toInt() <= 127) {
                listeLettre.add(c.toString())
                c++
            }

            // -----------------------------------------
            val listeNombre: MutableList<String?> = ArrayList()
            for (i in 0..999) {
                if (i < 10) {
                    listeNombre.add("00$i")
                } else if (i < 100) {
                    listeNombre.add("0$i")
                } else {
                    listeNombre.add(i.toString())
                }
            }

            // -----------------------------------------
            val listeKV: MutableList<HashMap<String, String?>> = ArrayList()
            for (j in 0 until quantityListToReturn) {
                Collections.shuffle(listeNombre)
                val dico = HashMap<String, String?>()
                var i = 0
                for (ltrs in listeLettre) {
                    dico[ltrs] = listeNombre[i]
                    i++
                }
                listeKV.add(dico)
            }
            return listeKV
        }

        fun hexOctBinGen(text: String): Array<String?> {
            val tableau = arrayOfNulls<String>(4)
            var hex: StringBuilder
            for (j in tableau.indices) {
                hex = StringBuilder()
                when (j) {
                    0 -> {
                        var i = 0
                        while (i < text.length) {
                            val c = text[i]
                            hex.append(c)
                            i++
                        }
                        tableau[j] = hex.toString()
                    }
                    1 -> {
                        var i = 0
                        while (i < text.length) {
                            val c = text[i]
                            val result = Integer.toHexString(c.code)
                            hex.append(result)
                            hex.append(' ')
                            i++
                        }
                        tableau[j] = hex.toString()
                    }
                    2 -> {
                        var i = 0
                        while (i < text.length) {
                            val c = text[i]
                            val result = Integer.toOctalString(c.code)
                            hex.append(result)
                            hex.append(' ')
                            i++
                        }
                        tableau[j] = hex.toString()
                    }
                    3 -> {
                        var i = 0
                        while (i < text.length) {
                            val c = text[i]
                            val result = Integer.toBinaryString(c.code)
                            hex.append(result)
                            hex.append(' ')
                            i++
                        }
                        tableau[j] = hex.toString()
                    }
                    else -> {
                    }
                }
            }
            return tableau
        }

        fun textTobinFile(context: Context, text: String, fileName: String) {
            val textToSave = binGen(text)
            val monFichier = fileCreateMethodsInFilesDirectory(context, fileName)
            fileWriteMethod(monFichier, textToSave)
        }

        fun decodeBinFileToText(file: File): String {
            val text = fileReadMethod(file)
            var stringBuilderLettre: StringBuilder
            val stringBuilderGlobal = StringBuilder()
            if (text.length < Int.MAX_VALUE) {
                var i = 0
                while (i < text.length - 7) {
                    stringBuilderLettre = StringBuilder()
                    for (j in 0..7) {
                        val k = i + j
                        stringBuilderLettre.append(text[k])
                    }
                    val binaire = stringBuilderLettre.toString().toInt(2)
                    val c = binaire.toChar()
                    stringBuilderGlobal.append(c)
                    i += 8
                }
                return stringBuilderGlobal.toString()
            }
            return "Too many binaries to decode > 256 Mo"
        }

        fun binGen(text: String): String {
            val bin = StringBuilder()
            for (i in 0 until text.length) {
                val c = text[i]
                var result = Integer.toBinaryString(c.code)
                if (result.length < 8) {
                    val strbuilder = StringBuilder()
                    result = if (result.length == 7) {
                        strbuilder.append("0")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else if (result.length == 6) {
                        strbuilder.append("00")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else if (result.length == 5) {
                        strbuilder.append("000")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else if (result.length == 4) {
                        strbuilder.append("0000")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else if (result.length == 3) {
                        strbuilder.append("00000")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else if (result.length == 2) {
                        strbuilder.append("000000")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    } else {
                        strbuilder.append("0000000")
                        for (j in 0 until result.length) {
                            strbuilder.append(result[j])
                        }
                        strbuilder.toString()
                    }
                }
                bin.append(result)
            }
            return bin.toString()
        }

        private fun getDecimalFromBin(binary: Int): Int {
            var bin = binary
            var decimal = 0
            var n = 0
            while (true) {
                if (bin == 0) {
                    break
                } else {
                    val temp = bin % 10
                    decimal += (temp * 2.0.pow(n.toDouble())).toInt()
                    bin /= 10
                    n++
                }
            }
            return decimal
        } /*
    My PowerLibrary.
    Cre4ted By : M@xim3 M0y4 ;)
    # 4d.61.78.69.6d.65.20.4d.6f.79.61.20.3b.29.
    # 115.141.170.151.155.145.40.115.157.171.141.40.73.51.
    # 01001101.01100001.01111000.01101001.01101101.01100101.
    # 00100000.
    # 01001101.01101111.01111001.01100001.
    # 00100000.
    # 00111011.00101001.
     */
    }
}