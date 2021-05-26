package com.example.map.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.map.ApiData.VehicleWS
import com.example.map.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory


class VehicleWebSocket : AppCompatActivity() {

    lateinit var webSocketClient: WebSocketClient
    private var WEB_SOCKET_URL = "wss://stgtessaractws.loconav.com/cable?token=Re_RxxzPi-5ZK_HmuUhG"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_web_socket)
    }

    override fun onResume() {

        super.onResume()
        initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
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

                Log.i("Websocket", "onOpen")
                suscribe()
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.i("Websocket", "onClose")
                unsuscribe()
            }

            override fun onMessage(message: String?) {
                Log.i("Websocket", "onMessage")
                Log.i("OnMessage Data","${message.toString()}")
                settextonUI(message)
            }

            override fun onError(ex: Exception?) {
                Log.i("Websocket", "onError")
                Log.e("OnError", "${ex?.message}")
            }

        }
    }

    fun suscribe() {
        Log.i("suscribe","called")

        webSocketClient.send(
            "{\"command\":\"subscribe\",\"identifier\":{\"channel\":\"VehicleUpdatesChannel\",\"vehicle_id\":\"13\"}}" )
    }

    fun unsuscribe() {

        webSocketClient.send(
            "{\n" +
                    "    \"command\": \"unsubscribe\",\n" +
                    "}"
        )

    }

    fun settextonUI(message: String?) {

        message?.let {

            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<VehicleWS> = moshi.adapter(VehicleWS::class.java)
            val data = adapter.fromJson(message)

           // Log.i("Data*****************", "${data}")
            //println(data)

            val txtOnUI: TextView = findViewById(R.id.txtViewWS)
            runOnUiThread {
                txtOnUI.text = "Message recieved is : ${message}"
            }
        }

    }
}


//{
//    "command": "subscribe",
//    "identifier": {
//    "channel": "VehicleUpdatesChannel",
//    "vehicle_id": "38630"
//}
//}