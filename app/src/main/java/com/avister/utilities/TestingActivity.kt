


package com.avister.utilities

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.avister.R
import com.avister.midiGeneration.saveAsMidi
import com.avister.ml.models.ModelType
import com.avister.ml.models.MusicGenerator
import com.avister.ml.models.MusicGeneratorAndroid
import org.jfugue.pattern.Pattern
import java.io.File
import java.io.FileOutputStream

class TestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
//        testSave()
//        val filename = "test.txt"
//        val outUri = filesDir.toString() + File.separator + filename
//        val outFile = File(outUri)
//        val isCreated = outFile.createNewFile()
//        val x = "s"
//        patternSaveAsMidiWithChordsTest()

//        val configurationManager = ConfigurationManager(this)
////        val musicGeneratorAndroid =
////            MusicGeneratorAndroid(this, configurationManager["modelFileName"][0], ModelType.CPC, )
//
//        val sead = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray
////        musicGeneratorAndroid.generateMidiPattern(24, sead)
//        val pattern: Pattern = musicGeneratorAndroid.generateMidiPattern(24, sead)
//        val file = pattern.saveAsMidi(this, filesDir, "sheet_" + currentDateTimeAsString() + ".mid")
    }

    fun patternSaveAsMidiWithChordsTest() {
//        testOnActivity {
            val noteString =
                listOf("C5q+E-5q+G5q", "D5q", "E5q", "F5q", "G5q", "A5q", "B5q", "C6q").joinToString(" ")
            val pattern = Pattern(noteString)
            pattern.saveAsMidi(this, this.filesDir, "test2.midi")
    }

    fun testSave(){
        val file = File("$filesDir/folderName")
        if (!file.exists()) {
            file.mkdirs()
        }
        val data = "this is my first insert data"
        val myappFile = File(
            file
                .toString() + File.separator + "myapp2.txt"
        )

        val x = myappFile.createNewFile()
        val y = 3
//        val fos = FileOutputStream(myappFile)
//        fos.write(data.toByteArray())
//        fos.close()
    }
    fun testUti() {
        val mainMusicDir = Environment.DIRECTORY_MUSIC
//        val mainMusicDir = Uri.parse(".")

        val resolver = contentResolver
//        val resolver = ContextWrapper(applicationContext)
//        val path = getExternalFilesDir(mainMusicDir)?.absolutePath?.toUri()!!
//        val path = Uri.parse("content:///$filesDir/lib")
//        val rPath: Uri = ContentUris.withAppendedId(filesDir.toUri(), 1)
        val path = filesDir.toUri()
        val auth = path.authority

        val directory: File = File(filesDir.toString())
        val files = directory.listFiles()
//        Log.d("Files", "Size: " + files.size)
        for (i in files.indices) {
            val x = files[i].name
        }
//        getResolver()
//        val permissions = arrayOf()
//        val path = Uri.parse(Environment.getRootDirectory().absolutePath)
        checkAndGrantPermission(this, this, Manifest.permission.READ_EXTERNAL_STORAGE, 1)
        val check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//        val columns = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.MIME_TYPE
//        )

//        val selection = MediaStore.Audio.Media.MIME_TYPE + " LIKE '%'"
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            val cursor = resolver.query(path, null, null, null, null) ?: return
            if (!cursor.moveToFirst()) {
                cursor.close()
                return
            }

            do {
                //do something with cursor
            } while (cursor.moveToNext())
            cursor.close()

        }
    }

    fun loadMidiFilesFromProvider(contentUri: Uri) {
        val f = File("$filesDir")
        val q = f.list()
        val fileList = f.listFiles()
        val v = fileList[0]
        val x = 3

    }
}