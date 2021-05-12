package com.example.diceroller

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (val songs:List<Song>):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //This will create ViewHolder and store view in it
        //first we have to make a view , for making view we need a Layout Inflator Class
        //Layout Inflmator convert xml file into java object
        val infaltor=LayoutInflater.from(parent.context)
        val view =infaltor.inflate(R.layout.item_view,parent,false )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  songs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtitle.text=songs[position].title
        holder.txtdescription.text=songs[position].description
        var color="#CCCCCC"
        if(position%2==0)
          color ="#EEEEEE"
        holder.container.setBackgroundColor(Color.parseColor(color))


    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var txtitle=itemView.findViewById<TextView>(R.id.txtTitle)
        var txtdescription=itemView.findViewById<TextView>(R.id.txtdescription)
        var container=itemView.findViewById<LinearLayout>(R.id.container)


    }

}

