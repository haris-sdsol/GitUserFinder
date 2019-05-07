package com.seattle.gituserfinder.views.splash.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.seattle.gituserfinder.R
import com.seattle.gituserfinder.views.search_user.view.SearchingActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, SearchingActivity::class.java))
            finish()
        }, 3000)
    }
}
