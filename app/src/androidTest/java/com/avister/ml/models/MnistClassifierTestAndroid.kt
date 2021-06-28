package com.avister.ml.models

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.avister.navigation.MainMenuActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class  MnistClassifierTestAndroid {

    fun (() -> Any?).shouldNotThrow() = try {
        invoke()
    } catch (ex: Exception) {
        throw Error("expected not to throw!", ex)
    }

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainMenuActivity::class.java)

    @Test
    fun testModelConstructors(): Unit {
        {
            activityTestRule.scenario.onActivity { MnistClassifier(it) }
        }.shouldNotThrow()
    }

    @Test
    fun testOpenAssets(): Unit {
        activityTestRule.scenario.onActivity {
            val mngr = it.getAssets().openFd("mnist.tflite")
            println("mngr:")
            println(mngr.toString())
        }
    }

//    @Test
//    fun testClassify4(): Unit {
//        activityTestRule.scenario.onActivity {
//            val path = "classifierTestPictures/4.png";
//
//
//            val bitmapOptions = BitmapFactory.Options()
//            bitmapOptions.inDither = true;// optional
//            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
//            val bitmap = BitmapFactory.decodeFile(path, bitmapOptions)
//
//            Log.d("Test","Bitmap data " + bitmap);
//            val mnistClassifier = MnistClassifier(it)
//            val bitmap =
//            mnistClassifier.classify()
//        }
//    }

}