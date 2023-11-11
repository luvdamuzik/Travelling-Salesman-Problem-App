package com.example.android.navigation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.math.roundToInt

class MapFragment : Fragment() {

    private val places = mutableListOf<List<String>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->

            prepareItems()
            val origin : String = places[0][0]
            val list : MutableList<MutableList<String>> = mutableListOf()

            for (i in 0 until places.size){
                if(places[i][0] == origin){
                    list.add(0, places[i] as MutableList<String>)
                } else
                    list.add(places[i] as MutableList<String>)
            }

            lateinit var permutacije : MutableList<MutableList<List<String>>>

            if(list.size == 1){
                permutacije = mutableListOf(mutableListOf(list[0]))
            }else{
                permutacije = list.slice(1 until list.size).toMutableList().permutations(list)
            }

            var optimalna : MutableList<List<String>> = permutacije[0]
            var minimalna = java.lang.Double.POSITIVE_INFINITY

            for (i in 0 until permutacije.size){
                val udaljenost: Double = distanceBetween(permutacije[i])
                println(udaljenost)
                if (udaljenost < minimalna){
                    minimalna = udaljenost
                    optimalna = permutacije[i]
                }
            }

            val koordinate_optimalnih: MutableList<LatLng> = mutableListOf()


            optimalna.forEach { place -> koordinate_optimalnih.add(LatLng(
                place[1].toDouble(),
                place[2].toDouble()
            )) }

            addMarkers(googleMap)

            val polyline1 = googleMap.addPolyline(
                PolylineOptions()
                .clickable(true)
                .color(Color.RED)
                .addAll(koordinate_optimalnih))

            polyline1.endCap = RoundCap()
            polyline1.width = 10.0F
            polyline1.jointType = JointType.ROUND

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(places[0][1].toDouble(), places[0][2].toDouble()), 7f))
            val distance = distanceBetween(optimalna).roundToInt()

            var ruta = ""
            for(i in 0 until optimalna.size){
                ruta += if(i == optimalna.size-1){
                    optimalna[i][0]
                } else
                    optimalna[i][0] + " -> "
            }
            Toast.makeText(activity, ruta, Toast.LENGTH_LONG).show()
            Toast.makeText(activity, "Udaljenost je: " + distance + " km", Toast.LENGTH_LONG).show()
        }
    }


    private fun prepareItems(){
        val filename = "gradovi"

        if(File(activity?.filesDir?.path,filename).exists()){
            var fileInputStream: FileInputStream? = context?.openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                text?.let { places.add(it.drop(1).dropLast(1).split(",")) }
            }
        }

    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            googleMap.addMarker(
                MarkerOptions()
                    .title(place[0])
                    .position(LatLng(place[1].toDouble(), place[2].toDouble()))
            )
        }
    }



    fun distanceBetween(list: MutableList<List<String>>): Double {
        var distance = 0.0
        for (i in 1 until list.size){
            val prvi = LatLng(list[i][1].toDouble(), list[i][2].toDouble())
            val drugi = LatLng(list[i-1][1].toDouble(), list[i-1][2].toDouble())
            distance += SphericalUtil.computeDistanceBetween(prvi,drugi)
        }
        return distance/1000
    }


    private fun  MutableList<MutableList<String>>.permutations(s: MutableList<MutableList<String>>):  MutableList<MutableList<List<String>>> {
        val retVal:  MutableList<MutableList<List<String>>> = mutableListOf()

        fun generate(k: Int, list: MutableList<MutableList<String>>) {
            if (k == 1) {
                retVal.add(list.toMutableList())
            } else {
                for (i in 0 until k) {
                    generate(k - 1, list)
                    if (k % 2 == 0) {
                        Collections.swap(list, i, k - 1)
                    } else {
                        Collections.swap(list, 0, k - 1)
                    }
                }
            }
        }

        generate(this.count(), this.toMutableList())
        for (i in 0 until retVal.size){
            retVal[i].add(0, s[0])
            retVal[i].add(s[0])
        }
        return retVal
    }

}
