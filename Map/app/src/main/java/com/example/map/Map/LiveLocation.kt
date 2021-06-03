package com.example.map.Map

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.map.R
import com.example.map.ViewModel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LiveLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var marker: Marker
    private val viewModel: MapViewModel by viewModels()
    lateinit var  mapFragment:SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_location)
        mapFragment = supportFragmentManager
           .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        var placeMarker = LatLng(0.0, 0.0)

        marker = map.addMarker(
            MarkerOptions().position(placeMarker)
                .title(placeMarker.toString())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck20px))
        )
        val zoomlevel = 12f
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(placeMarker, zoomlevel))
        marker.showInfoWindow()

        println(Thread.currentThread().name)

        viewModel.data.observe(this) { data ->
            placeMarker= LatLng(data?.latitude!!,data?.longitude!!)
            marker.snippet="${data?.speed}"
            marker.title
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(placeMarker, zoomlevel))
            marker.showInfoWindow()
        }

    }

}




