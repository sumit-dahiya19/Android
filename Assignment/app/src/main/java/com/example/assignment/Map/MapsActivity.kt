package com.example.assignment.Map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private  var lat:Double?=null
    private  var long:Double?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lat=intent.getDoubleExtra("lat",28.7471767)
        long=intent.getDoubleExtra("long",77.1148815)
        //lat=intent.getStringExtra("lat")?.toDouble()
        //long = intent.getStringExtra("long")?.toDouble()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        println("In map activity ****************************")
        println(lat)
        println(long)

        val homelatlng = LatLng(lat!!, long!!)
        val zoomlevel = 18f

        map.addMarker(MarkerOptions().position(homelatlng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homelatlng, zoomlevel))
    }
}