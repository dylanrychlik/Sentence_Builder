package com.example.sentencebuilder

import android.app.Application

class MyApplication : Application() {
    val sharedRepository: SharedRepository = SharedRepository()

}