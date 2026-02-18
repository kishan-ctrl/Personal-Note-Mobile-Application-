package com.example.personaltasknotes

import android.app.Application
import com.example.personaltasknotes.data.local.db.AppDatabase

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}