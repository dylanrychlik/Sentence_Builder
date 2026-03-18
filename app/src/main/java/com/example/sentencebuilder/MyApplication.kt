package com.example.sentencebuilder

import android.app.Application

class MyApplication : Application() {
    val sharedRepository: SharedRepository by lazy {
        SharedRepository(applicationContext)
    }
}