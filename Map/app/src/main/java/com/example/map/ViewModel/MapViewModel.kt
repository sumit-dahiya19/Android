package com.example.map.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.map.ApiData.Payload
import com.example.map.ApiData.VehicleWS
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class MapViewModel : ViewModel() {
    private var WEB_SOCKET_URL = "wss://stgtessaractws.loconav.com/cable?token=Re_RxxzPi-5ZK_HmuUhG"
    lateinit var webSocketClient: WebSocketClient

    init {
        initWebSocket()
    }

    private var _data = MutableLiveData<Payload?>()
    val data: LiveData<Payload?>
        get() = _data

    private fun initWebSocket() {
        val uri = URI(WEB_SOCKET_URL)
        createWebSocketClient(uri)

        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient.setSocketFactory(socketFactory)

        webSocketClient.connect()

    }

    private fun suscribe() {
        webSocketClient.send(
            "{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"VehicleUpdatesChannel\\\",\\\"vehicle_id\\\":13}\"}"
        )
    }

    private fun unsuscribe() {
        webSocketClient.send(
            "{\n" +
                    "    \"command\": \"unsubscribe\",\n" +
                    "}"
        )
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

                try {
                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()

                    val adapter: JsonAdapter<VehicleWS> = moshi.adapter(VehicleWS::class.java)

                    val temp = adapter.fromJson(message)
                    setbottle(temp)


                } catch (ex: JsonDataException) {
                }
            }

            override fun onError(ex: Exception?) {
                Log.e("Error", "No Data")
            }
        }
    }

    private fun setbottle(temp: VehicleWS?) {
           _data=MutableLiveData(temp?.message?.payload)
//           println("${_data.value}")
    }
}





