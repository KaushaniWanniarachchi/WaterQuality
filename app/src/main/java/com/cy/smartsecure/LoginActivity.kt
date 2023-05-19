package com.cy.smartsecure

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class LoginActivity : AppCompatActivity() {


    private val mContinueBtn: Button by lazy { findViewById(R.id.continue_btn) }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val intent = Intent(this, Register_javaActivity::class.java)
        startActivity(intent)


        mContinueBtn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}