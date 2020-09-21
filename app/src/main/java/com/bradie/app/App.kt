package com.bradie.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * {@annotation HiltAndroidApp} is required by the Dagger-Hilt to inject dependencies
 * into the Application.
 *
 * class App must be set in manifest.
 */
@HiltAndroidApp
class App : Application()
