package com.loe.fanmark.util

import android.app.Application
import android.content.Context

class Application: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()

        val context: Context = getContext()

    }
}