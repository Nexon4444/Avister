package com.avister.utilities

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.avister.R
import java.security.Permission

class TestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
        testUti()
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
}