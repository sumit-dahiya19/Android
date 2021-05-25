package com.example.map

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.Activity.WebSockett
import com.example.map.Network.ServiceBuilder
import com.example.map.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = ServiceBuilder.buildService(ApiService::class.java)
        val requestcall = service.getdata()
        requestcall.enqueue(object : Callback<VechicleInfo> {
            override fun onFailure(call: Call<VechicleInfo>, t: Throwable) {
                Toast.makeText(baseContext, "Failed to fetch data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<VechicleInfo>, response: Response<VechicleInfo>) {
                if (response.isSuccessful) {
                    val adapter = MyAdapter(response.body()!!)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager=LinearLayoutManager(baseContext)

                } else
                    Toast.makeText(baseContext, "Some Error Occured ", Toast.LENGTH_LONG).show()
            }

        })

        binding.button.setOnClickListener{

            val intent = Intent(binding.root.context, WebSockett::class.java)
            ContextCompat.startActivity(binding.root.context, intent, null)

        }
    }
}

/* TO PRINT API DATA IN SINGLE TEXTVIEW IN ANDROID APP

fun loadDataApi(binding: ActivityMainBinding) {
    val service = ServiceBuilder.buildService(ApiService::class.java)
    val requestcall = service.getdata()
    requestcall.enqueue(object : Callback<String> {

        override fun onFailure(call: Call<String>, t: Throwable) {
            //Toast.makeText(parent.context,"Failed",Toast.LENGTH_LONG).show()
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            if(response.isSuccessful){
                 var result= response.body().toString()!!
                binding.apidata.text=result
            }
        }
    })
}
*/

