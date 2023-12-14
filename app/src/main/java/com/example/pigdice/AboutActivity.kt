package com.example.pigdice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun imgViewOnClick( vw: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun btnSupportOnClick( vw: View){
        val uri: Uri = Uri.parse("https://www.lockersoft.com")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun btnAboutOnClick( vw: View){
        val uri: Uri = Uri.parse("https://www.lockersoft.com/webpages/about.php")
        startActivity(Intent(Intent.ACTION_VIEW, uri))

    }
}