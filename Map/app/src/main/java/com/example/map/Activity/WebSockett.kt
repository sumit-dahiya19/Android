package com.example.map.Activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.map.ApiData.BitCoinPrice
import com.example.map.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class WebSockett : AppCompatActivity() {

    lateinit var webSocketClient: WebSocketClient
    private val WEB_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_sockett)
    }

    override fun onResume() {
        super.onResume()
        intiWebSocket()
    }

    override fun onPause() {
        super.onPause()
        webSocketClient.close()
    }

    private fun intiWebSocket() {
        //1. first thing we need is an URI object to pass it to our WebSocketClient
        val coinbaseUri: URI? = URI(WEB_SOCKET_URL)
        //2. The method will contain an anonymous class of WebSocketClient with the implemented logic for onOpen(), onMessage(), onClose(), onError() and it will be assigned to our field webSocketClient.
        createWebSocketClient(coinbaseUri)

        //3.This local variable is required for connecting to a secure websocket because the “wss” from “wss://ws-feed.pro.coinbase.com” stands for websocket secure
        //The connection is encrypted. If it would be “ws” the connection would be in cleartext.
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        //Below the local variable of type SSLSocketFactory we need to set the socket factory for our field webSocketClient.
        webSocketClient.setSocketFactory(socketFactory)
        // At a new line, we can call the connect() method of our field webSocketClient to establish the connection to “wss://ws-feed.pro.coinbase.com”.
        webSocketClient.connect()

    }

    private fun createWebSocketClient(coinbaseUri: URI?) {
        webSocketClient = object : WebSocketClient(coinbaseUri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.i("WebSocket", "OnOpen")
                //3.To begin receiving feed messages, you must first send a subscribe message to the server indicating which channels and products to receive.
                // This message is mandatory — you will be disconnected if no subscribe has been received within 5 seconds.
                suscribe()
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.i("WebSocket", "OnClose")
                //If you want to unsubscribe from channel/product pairs, send an unsubscribe message.
                unsubscribe()
            }

            override fun onMessage(message: String?) {
                Log.i("WebSocket", "OnMessage")
                // we recieved message just have to set it to textview now
                setUpBtcPriceText(message)
            }

            override fun onError(ex: Exception?) {
                Log.i("WebSocket", "OnError")
                Log.e("OnError", "${ex?.message}")

            }

        }
    }

    fun suscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                    "}"
        )
    }

    private fun unsubscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"type\": \"unsubscribe\",\n" +
                    "    \"channels\": [\"ticker\"]\n" +
                    "}"
        )
    }

    fun setUpBtcPriceText(message: String?) {
        //task-> Display the message to textview
        // 1.The given parameter should first be checked if it is not null
        // 2.Create a moshi instance. Create the adapter instance in the next line
        message?.let {
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<BitCoinPrice> = moshi.adapter(BitCoinPrice::class.java)
            val bitcoin = adapter.fromJson(message)
            val btc_price_tv: TextView = findViewById<TextView>(R.id.btc_price_tv)
            runOnUiThread {
                btc_price_tv.text = "1 BTC value is : ${bitcoin?.price}"
            }
        }
    }


}
//////closeheaders :  Origin : https://loconav.com
//////"wss://tessaract.loconav.com:3334/cable?token=tLBkwpgbkyRcxMdt5jgm"
//////{
//////    command : subscribe
//////    identifier : {
//////    channel : VehicleUpdatesChannel
//////    vehicle_id : 38630
//////}
//////}
////
////
////

