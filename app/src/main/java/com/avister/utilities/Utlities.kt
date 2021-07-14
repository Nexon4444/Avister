package com.avister.utilities

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*


fun currentDateTimeAsString(): String {
     val calendar = Calendar.getInstance()
     val cDay = calendar.get(Calendar.DAY_OF_MONTH)
     val cMonth = calendar.get (Calendar.MONTH)
     val cYear = calendar.get(Calendar.YEAR)
//     val selectedMonth = "" + cMont)
//     val selectedYear = "" + cYea)
     val cHour = calendar.get(Calendar.HOUR)
     val cMinute = calendar.get(Calendar.MINUTE)
     val cSecond = calendar.get(Calendar.SECOND)
     val datetimeList = listOf(cYear, cMonth, cDay, cHour, cMinute, cSecond)
     return datetimeList.joinToString("_")
}

// Function to check and request permission.
fun checkAndGrantPermission(context: Context, activity: Activity, permission: String, requestCode: Int) {
     if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
          // Requesting the permission
          ActivityCompat.requestPermissions(
               activity,
               arrayOf(permission),
               requestCode
          )
     } else {
          Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()
     }
}


