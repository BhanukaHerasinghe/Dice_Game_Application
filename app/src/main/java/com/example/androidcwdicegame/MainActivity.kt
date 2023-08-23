
//Video Link - https://drive.google.com/drive/folders/1FqFG16QcLypV5lEQOrsA1n-ub5LOYVj-?usp=share_link

// Bhanuka Herasinghe

// w1870109 -20200468

package com.example.androidcwdicegame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons from the layout file
        val newbutton = findViewById<Button>(R.id.newgamebutton)
        val aboutbutton = findViewById<Button>(R.id.aboutbutton)




        // Set click listeners for the buttons
        aboutbutton.setOnClickListener { my_button_onClick_working(aboutbutton) }
        newbutton.setOnClickListener{
            val opengame = Intent(this,newgame::class.java)
            startActivity(opengame)
        }

    }

    // Show a popup window when the "about" button is clicked
    fun my_button_onClick_working(view: View?) {

        // step 1 : Inflate the layout for the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.gamepop, null)

        // step 2 : Create the popup window with the inflated layout
        val wid = LinearLayout.LayoutParams.WRAP_CONTENT
        val high = LinearLayout.LayoutParams.WRAP_CONTENT
        val focus= true
        val popupWindow = PopupWindow(popupView, wid, high, focus)

        // step 3 : Show the popup window at the center of the view passed as a parameter
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

    }
}