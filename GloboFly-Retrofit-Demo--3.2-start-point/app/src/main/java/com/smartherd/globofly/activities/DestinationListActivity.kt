package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.smartherd.globofly.R
import com.smartherd.globofly.Services.DestinationService
import com.smartherd.globofly.Services.ServiceBuilder
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable

class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

		// To be replaced by retrofit code
		//destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

		val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
		//val destinationService=ServiceBuilder.serviceCreate(DestinationService::class.java)

		val requestcall = destinationService.getDestinationList()
		requestcall.enqueue(object : Callback<List<Destination>> {

			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if(response.isSuccessful){
					val list=response.body()!!
					Log.i("OnResponce","Done")
					destiny_recycler_view.layoutManager=LinearLayoutManager(this@DestinationListActivity)
					destiny_recycler_view.adapter = DestinationAdapter(list)
				}
				else{
					//application level failure
					Toast.makeText(this@DestinationListActivity,"Failed to retrive items",Toast.LENGTH_LONG)
				}

			}
			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
				Toast.makeText(this@DestinationListActivity, "Error Occured ${t.toString()}",Toast.LENGTH_LONG)
			}

		})
	}
}