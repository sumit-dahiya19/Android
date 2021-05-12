package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val arr= mutableListOf<Song>()
        arr.add(Song("Hello","1"))
        arr.add(Song("A","2"))
        arr.add(Song("B","3"))
        arr.add(Song("C","4"))
        arr.add(Song("D","5"))
        arr.add(Song("E","6"))
        arr.add(Song("F","7"))
        arr.add(Song("G","8"))
        arr.add(Song("H","9"))
        arr.add(Song("I","10"))
        arr.add(Song("J","11"))




        val songList:RecyclerView=findViewById(R.id.songList)
        songList.adapter = MyAdapter(arr)
        songList.layoutManager = LinearLayoutManager(this)
    }

}

