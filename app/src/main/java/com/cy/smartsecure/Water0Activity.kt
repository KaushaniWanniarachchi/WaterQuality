package com.cy.smartsecure

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible

class Water0Activity : AppCompatActivity() {


    private val mHisBtn: Button by lazy { findViewById(R.id.History_Btn) }
    private val mResult: TextView by lazy { findViewById(R.id.Result) }
    private val mLocBtn: Button by lazy { findViewById(R.id.save_location_btn) }
    private val mHisLayout: LinearLayout by lazy { findViewById(R.id.Historylayout) }
    private val mFinalLayout: LinearLayout by lazy { findViewById(R.id.FinalreLayout) }
    private val mviewHistory: TextView by lazy { findViewById(R.id.viewHistory) }




    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water0)

        mviewHistory.setOnClickListener(){
            mFinalLayout.isVisible = false
            mHisLayout.isVisible = true
        }

        mFinalLayout.setOnClickListener(){
            mHisLayout.isVisible = false
            mFinalLayout.isVisible = true
        }




      mviewHistory.setOnClickListener {
            mviewHistory.background = resources.getDrawable(R.drawable.switch_trcks,null)
            mviewHistory.setTextColor(resources.getColor(R.color.textColor,null))
            mResult.background = null
            mHisLayout.visibility = View.VISIBLE
            mFinalLayout.visibility = View.GONE
            mResult.setTextColor(resources.getColor(R.color.BlueColor,null))
        }
        mResult.setOnClickListener {
            mviewHistory.background = null
            mviewHistory.setTextColor(resources.getColor(R.color.BlueColor,null))
            mResult.background = resources.getDrawable(R.drawable.switch_trcks,null)

            mHisLayout.visibility = View.GONE
            mFinalLayout.visibility = View.VISIBLE
            mResult.setTextColor(resources.getColor(R.color.textColor,null))
        }
        mLocBtn.setOnClickListener {
            startActivity(Intent(this@Water0Activity,Water4Activity::class.java))
        }
    }
        }





