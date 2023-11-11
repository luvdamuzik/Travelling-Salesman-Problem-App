package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.*


class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.apiKeyPlaces)

        if (!Places.isInitialized()) {
            activity?.applicationContext?.let { Places.initialize(it, apiKey) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val autocompleteSupportFragment1 = childFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?

        autocompleteSupportFragment1?.setTypeFilter(TypeFilter.CITIES)
        autocompleteSupportFragment1?.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        autocompleteSupportFragment1?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val textView = view?.tv1

                val pokusaj =
                    place.name?.let { listOf(it, place.latLng?.latitude.toString(),
                        place.latLng?.longitude.toString()
                    ) }.toString()

                val filename = "gradovi"
                val outputStream: FileOutputStream
                val stringBuilder: StringBuilder = StringBuilder()

                if(File(activity?.filesDir?.path,filename).exists()){
                    val fileInputStream: FileInputStream? = context?.openFileInput(filename)
                    val inputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)

                    var text: String?
                    while (run {
                            text = bufferedReader.readLine()
                            text
                        } != null) {
                        stringBuilder.appendLine(text)
                    }
                }
                stringBuilder.append(pokusaj)

                try {
                    outputStream = context?.openFileOutput(filename, Context.MODE_PRIVATE)!!
                    outputStream.write(stringBuilder.toString().toByteArray())
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val name = place.name
                val latlng = place.latLng
                val latitude = latlng?.latitude
                val longitude = latlng?.longitude

                if (textView != null) {
                    textView.text = "Name: $name+ Latitude, Longitude: $latitude , $longitude"
                }
            }

            override fun onError(status: Status) {
                Toast.makeText(activity?.applicationContext,"Some error occurred", Toast.LENGTH_SHORT).show()
            }

        })

    }
}