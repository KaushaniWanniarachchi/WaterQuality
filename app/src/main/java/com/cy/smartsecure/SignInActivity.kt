package com.cy.smartsecure

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {
    private val mLoginBtn: Button by lazy { findViewById(R.id.LoginBtn) }

    fun goToRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()

    }


    private lateinit var Auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        mLoginBtn.setOnClickListener {
            val intent = Intent(this, Water0Activity::class.java)
            startActivity(intent)
        }
    }
}
        /*

        // Initialize Firebase Auth
         Auth = Firebase.auth
        // Initialize Firebase Auth
             //mAuth = FirebaseAuth.getInstance()

        // Add code to check if the user is already logged in
        val currentUser = Auth.currentUser
        if (currentUser != null) {
            // User is already logged in, so redirect to Water0Activity
            val intent = Intent(this, Water0Activity::class.java)
            startActivity(intent)
            finish()
        }

        // Add click listener to login button
         val mLoginBtn: Button by lazy { findViewById(R.id.LoginBtn)}
        mLoginBtn.setOnClickListener {
            val userNameEditText: EditText = findViewById(R.id.User_Name)
            val passwordEditText: EditText = findViewById(R.id.Password)

            val userName = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            Auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login was successful, so redirect to Water0Activity
                        val intent = Intent(this, Water0Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed, so display an error message
                        Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}


*/