package com.example.map

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.map.Map.MapsActivity

class MyAdapter(private val obj: VechicleInfo) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var arrData = obj.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        println(arrData)
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(position)

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = itemView.findViewById<TextView>(R.id.id)
        var displayNumber: TextView = itemView.findViewById<TextView>(R.id.display_number)
        var container = itemView.findViewById<LinearLayout>(R.id.container)

        fun setData(position: Int) {
            id.text = arrData[position].id.toString()
            displayNumber.text = arrData[position].displayNumber
            container.setOnClickListener {

                clickAction(position)
            }
        }

        fun clickAction(position: Int) {
            if (arrData[position].lastLocation.lat != null && arrData[position].lastLocation.long != null) {
                val intent = Intent(container.context, MapsActivity::class.java)

                intent.putExtra(
                    "lat",
                    arrData[position].lastLocation.lat!!
                )
                intent.putExtra(
                    "long",
                    arrData[position].lastLocation.long!!
                )
                intent.putExtra(
                    "speed",
                    arrData[position].lastLocation.speed
                )

                ContextCompat.startActivity(container.context, intent, null)

            } else {
                Toast.makeText(container.context, "No Data Available", Toast.LENGTH_SHORT).show()

            }

        }
    }
}