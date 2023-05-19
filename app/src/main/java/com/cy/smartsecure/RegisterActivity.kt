package com.cy.smartsecure

import  android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    fun goToLogin(view: View){
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private lateinit var auth: FirebaseAuth
    private val mSubBtn: AppCompatButton by lazy { findViewById(R.id.submit) }
    private val mFirstname: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.First_Name) }
    private val mLastname: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.Last_Name) }
    private val mNic: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.NIC_Number) }
    private val mMobile: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.Mobile_Number) }
    private val mEmail: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.Email_Address) }
    private val mPassword: AppCompatAutoCompleteTextView by lazy { findViewById(R.id.Password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
       // auth = Firebase.auth
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Handle Submit button click
        mSubBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val firstName = mFirstname.text.toString().trim()
        val lastName = mLastname.text.toString().trim()
        val mobileNumber = mMobile.text.toString().trim()
        val nic = mNic.text.toString().trim()
        val email = mEmail.text.toString().trim()
        val password = mPassword.text.toString().trim()

        // Validate user inputs
        if (firstName.isEmpty()) {
            mFirstname.error = "First name is required"
            mFirstname.requestFocus()
            return
        }

        if (lastName.isEmpty()) {
            mLastname.error = "Last name is required"
            mLastname.requestFocus()
            return
        }

        if (mobileNumber.isEmpty()) {
            mMobile.error = "Mobile number is required"
            mMobile.requestFocus()
            return
        }

        if (nic.isEmpty()) {
            mNic.error = "NIC is required"
            mNic.requestFocus()
            return
        }

        if (email.isEmpty()) {
            mEmail.error = "Email is required"
            mEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.error = "Please enter a valid email"
            mEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            mPassword.error = "Password is required"
            mPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            mPassword.error = "Password should be at least 6 characters long"
            mPassword.requestFocus()
            return
        }

        // Register user with Firebase
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    auth.currentUser
                    Toast.makeText(
                        this, "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Water0Activity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this, "Registration failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
