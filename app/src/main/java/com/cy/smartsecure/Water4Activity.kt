package com.cy.smartsecure

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.FirebaseApp

//import com.google.firebase.database.FirebaseDatabase

class Water4Activity : AppCompatActivity() {

    private val mSubmitBtn: AppCompatButton by lazy { findViewById(R.id.Submit) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water4)

        FirebaseApp.initializeApp(this)

        mSubmitBtn.setOnClickListener {
          //  val database = FirebaseDatabase.getInstance().reference
            val howToSave = findViewById<EditText>(R.id.how_to_save).text.toString()
            val address = findViewById<EditText>(R.id.address).text.toString()
            val location = findViewById<EditText>(R.id.location).text.toString()

            val data = HashMap<String, String>()
            data["how_to_save"] = howToSave
            data["address"] = address
            data["location"] = location

           // database.child("locations").push().setValue(data)

            val intent = Intent(this, Water0Activity::class.java)
            startActivity(intent)
        }
    }
}
