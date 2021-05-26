package com.example.map.Map

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.map.ApiData.VehicleWS
import com.example.map.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class LiveLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    lateinit var webSocketClient: WebSocketClient
    private var WEB_SOCKET_URL = "wss://stgtessaractws.loconav.com/cable?token=Re_RxxzPi-5ZK_HmuUhG"
    private var speed: Long? = null
    private lateinit var marker: Marker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initWebSocket()
    }

    private fun initWebSocket() {
        val uri = URI(WEB_SOCKET_URL)
        createWebSocketClient(uri)

        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)
        webSocketClient.connect()
    }

    private fun createWebSocketClient(uri: URI) {
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                suscribe()
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                unsuscribe()
            }

            override fun onMessage(message: String?) {
                set(message)
            }

            override fun onError(ex: Exception?) {
                Log.e("Error", "In WebSocket")
            }

        }
    }

    fun suscribe() {
        webSocketClient.send(
            "{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"VehicleUpdatesChannel\\\",\\\"vehicle_id\\\":13}\"}"
        )
    }

    fun unsuscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"command\": \"unsubscribe\",\n" +
                    "}"
        )
    }

    fun set(message: String?) {

        message.let {

            try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val adapter: JsonAdapter<VehicleWS> = moshi.adapter(VehicleWS::class.java)

                val data = adapter.fromJson(message)


                runOnUiThread {

                    val lat = data?.message?.payload?.latitude
                    val long = data?.message?.payload?.longitude
                    speed = data?.message?.payload?.speed

                    val latLong = LatLng(lat!!, long!!)

                    marker.position = latLong
                    marker.title = latLong.toString()
                    marker.snippet = "speed $speed"
                    marker.showInfoWindow()

                    val zoomlevel = 15f
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, zoomlevel))
                }

            } catch (ex: JsonDataException) {
                println("error")
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val homelatlong = LatLng(0.0, 0.0)

        marker = map.addMarker(
            MarkerOptions().position(homelatlong)
                .title(homelatlong.toString())
                .snippet("Speed:$speed")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
    }

}