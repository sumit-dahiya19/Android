package com.example.map

import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

     private lateinit var map: GoogleMap

    private val TAG = MapsActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latitude = 28.7471767
        val longitude = 77.1148815
        val homelatlng = LatLng(latitude, longitude)
        val zoomlevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homelatlng, zoomlevel))

        val overlaySize = 100f
        val androidOverlay = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.android))
                .position(homelatlng, overlaySize)
        map.addGroundOverlay(androidOverlay)


        map.addMarker(MarkerOptions().position(homelatlng))

        setMapLongClick(map) // Just add a marker nothing else

        setPoiClick(map) // display the POI's name in an info window.

        setMapStyle(map)

        enableMyLocation()



        //add info for the marker like displaying coordinates

        // Add a marker in Sydney and move the camera
        /*val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapClickListener {
            val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    it.latitude,
                    it.longitude
            )    // A snippet is additional text displayed after the title.->Your snippet displays the latitude and longitude of a marker.
            map.addMarker(
                    MarkerOptions()
                            .position(it)
                            .title(getString(R.string.dropped_pin))
                            .snippet(snippet)
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {

        map.setOnPoiClickListener {
            val poiMaker = map.addMarker(
                    MarkerOptions().position(it.latLng)
                            .title(it.name)
            )
            poiMaker.showInfoWindow()

        }
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
                Log.e(TAG, "Styling failed")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "can't find the style", e)
        }
    }

    private val REQUEST_LOCATION_PERMISSION = 1
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation(){
        if(isPermissionGranted()){
            map.isMyLocationEnabled = true
        }
        else{
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

}