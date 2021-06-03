package com.example.map.Map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.map.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var lat: Double? = null
    private var long: Double? = null
    private var speed: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        lat = intent.getDoubleExtra("lat", 28.7471767)
        long = intent.getDoubleExtra("long", 77.1148815)
        speed = intent.getDoubleExtra("speed", 0.0)
        //lat=intent.getStringExtra("lat")?.toDouble()
        //long = intent.getStringExtra("long")?.toDouble()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        println("In map activity ****************************")
        Log.i("lat value", "$lat")
        Log.i("long value", "$long")
        Log.i("speed value", "$speed")


        val homelatlng = LatLng(lat!!, long!!)
        val zoomlevel = 18f

        map.addMarker(MarkerOptions().position(homelatlng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homelatlng, zoomlevel))

        setMapStyle(map)

        speedMarker(map, homelatlng) // Info window
    }

    fun setMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e("In Styling Map", "Styling failed")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("In Styling Map", "can't find the style", e)
        }
    }

    private fun speedMarker(map: GoogleMap, homelatlong: LatLng) {

        val marker = map.addMarker(
            MarkerOptions().position(homelatlong)
                .title(homelatlong.toString())
                .snippet("Speed:$speed")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
        marker.showInfoWindow()

    }
}
