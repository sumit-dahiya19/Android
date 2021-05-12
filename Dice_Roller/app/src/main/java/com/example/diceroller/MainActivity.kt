package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.button)
        button.setOnClickListener(){
            Toast.makeText(applicationContext,"Toast Message", Toast.LENGTH_SHORT).show()
        }

        val button_snack:Button = findViewById(R.id.button_snackbar)
        button_snack.setOnClickListener(){
            Snackbar.make(it, "SnackBar Single Line", Snackbar.LENGTH_SHORT)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction("Action"){
                    Toast.makeText(this,"Snackbar Message", Toast.LENGTH_SHORT).show()
                }
                .show()


        }
    }

}

