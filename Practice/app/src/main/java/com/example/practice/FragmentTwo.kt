package com.example.practice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentTwo:Fragment() {

    val TAG="Second Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"oncreate")
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,"OncreateView")
        return inflater.inflate(R.layout.fragment_two,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG,"onViewCreated,here we can change view attributes")

    }



}