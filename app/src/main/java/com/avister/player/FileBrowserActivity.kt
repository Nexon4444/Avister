/*
 * Copyright (c) 2011-2012 Madhav Vaidyanathan
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */
package com.avister.player

import android.app.ListActivity
import android.content.ContextWrapper
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.avister.R
import com.avister.navigation.ChooseSongActivity.Companion.openFile
import com.avister.utilities.ConfigurationManager
import java.io.File
import java.util.*

class FileBrowserActivity : ListActivity() {
    private val LOG_TAG = FileBrowserActivity::class.java.simpleName
    private var directory /* Current directory being displayed */: File? = null
    private var directoryView /* TextView showing directory name */: TextView? = null
    private val rootdir /* The top level root directory */: File
    private val configManager = ConfigurationManager(this)
    init {
        val cw = ContextWrapper(this)
        rootdir = cw.getExternalFilesDir(configManager["mainMusicDir"] as String)!!
    }
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.file_browser)
        title = "avister.player: Browse Files"
    }

    public override fun onResume() {
        super.onResume()

//        rootdir = Environment.getExternalStorageDirectory().absolutePath
//        rootdir = configManager["mainMusicDir"]

//        rootdir =
//    val pattern: Pattern = Pattern("C4")
        val output = rootdir
        directoryView = findViewById(R.id.directory)
        val settings = getPreferences(0)
        val lastBrowsedDirectory = settings.getString("lastBrowsedDirectory", null)
        lastBrowsedDirectory?.let { loadDirectory(File(it)) } ?: loadDirectory(rootdir)
    }

    /* Scan the files in the new directory, and store them in the filelist.
     * Update the UI by refreshing the list adapter.
     */
    private fun loadDirectory(newDirectory: File) {
        if (newDirectory.toURI().toString() == "../") {
            try {
                directory = directory?.parentFile
            } catch (e: Exception) {
                Log.e(
                    LOG_TAG,
                    Thread.currentThread().stackTrace[2].methodName,
                    e
                )
            }
        } else {
            directory = newDirectory
        }
        // Do not navigate to root directory
        if (directory?.toURI().toString()  == "/" || directory?.toURI().toString()  == "//") {
            return
        }
        val editor = getPreferences(0).edit()
        editor.putString("lastBrowsedDirectory", directory?.toURI().toString())
        editor.apply()
        directoryView!!.text = directory?.toURI().toString()

        /* List of files in the directory */
        val filelist = ArrayList<FileUri>()
        val sortedDirs = ArrayList<FileUri>()
        val sortedFiles = ArrayList<FileUri>()
        val dir = directory
        // If we're not at the root directory, add parent directory to the list
        if (dir?.compareTo(rootdir) != 0) {
            val parentDirectory = directory?.parentFile
            val uri = Uri.parse("file://$parentDirectory")
            sortedDirs.add(FileUri(uri, "../"))
        }
        try {
            Log.e(LOG_TAG, "is root?: " + dir?.compareTo(rootdir))
            val files = dir?.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file == null) {
                        continue
                    }
                    val filename = file.name
                    if (file.isDirectory) {
                        val uri =
                            Uri.parse("file://" + file.absolutePath + "/")
                        val fileuri = FileUri(uri, file.name)
                        sortedDirs.add(fileuri)
                    } else if (filename.endsWith(".mid") || filename.endsWith(".MID") ||
                        filename.endsWith(".midi") || filename.endsWith(".MIDI")
                    ) {
                        val uri =
                            Uri.parse("file://" + file.absolutePath)
                        val fileuri = FileUri(uri, uri.lastPathSegment)
                        sortedFiles.add(fileuri)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(
                LOG_TAG,
                Thread.currentThread().stackTrace[2].methodName,
                e
            )
        }
        if (sortedDirs.size > 0) {
            Collections.sort(sortedDirs, sortedDirs[0])
        }
        if (sortedFiles.size > 0) {
            Collections.sort(sortedFiles, sortedFiles[0])
        }
        filelist.addAll(sortedDirs)
        filelist.addAll(sortedFiles)
        val adapter =
            IconArrayAdapter(this, android.R.layout.simple_list_item_1, filelist)
        this.listAdapter = adapter
    }

    /** When a user selects an item:
     * - If it's a directory, load that directory.
     * - If it's a file, open the SheetMusicActivity.
     */
    override fun onListItemClick(
        parent: ListView,
        view: View,
        position: Int,
        id: Long
    ) {
        super.onListItemClick(parent, view, position, id)
        val file = this.listAdapter.getItem(position) as FileUri
        if (file.isDirectory) {
            val path = File(file.uri.path!!)
            loadDirectory(path)
        } else {
            openFile(file)
        }
    }

    fun onHomeClick(view: View?) {
        loadDirectory(rootdir)
    }
}