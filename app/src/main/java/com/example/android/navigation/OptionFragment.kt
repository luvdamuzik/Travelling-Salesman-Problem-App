package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class OptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_option, container, false)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<View?>(R.id.delete)?.setOnClickListener {
            Toast.makeText(activity,"Obirsao",Toast.LENGTH_SHORT).show()
            /* val filename = "gradovi"

             if(File(activity?.filesDir?.path,filename).exists()){
                 val fileInputStream: FileInputStream? = context?.openFileInput(filename)
                 val inputStreamReader = InputStreamReader(fileInputStream)
                 val bufferedReader = BufferedReader(inputStreamReader)

                 var text: String?
                 while (run {
                         text = bufferedReader.readLine()
                         text
                     } != null) {

                 }
             }*/
        }
    }
}