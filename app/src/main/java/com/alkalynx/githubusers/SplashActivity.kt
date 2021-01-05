package com.alkalynx.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handlerThread = HandlerThread("loading")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.postDelayed(Runnable{
            val intent = Intent(this, MainActivity::class.java)
            intent.apply {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }, 5000)


    }
}