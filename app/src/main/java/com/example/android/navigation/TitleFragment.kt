package com.example.android.navigation

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.databinding.FragmentTitleBinding
import com.example.navigation.CustomAdapter
import kotlinx.android.synthetic.main.fragment_title.*
import java.io.*


class TitleFragment : Fragment() {

    private val itemsList = mutableListOf<List<String>>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)

        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_bar_menu, menu)

        val searchView = menu.findItem(R.id.search)
            .actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filter(query.toString());
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if(id == R.id.search) {true} else NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun filter(text: String) {
        val filterdNames: ArrayList<List<String>> = ArrayList()

        for (s in itemsList) {
            if (s[0].lowercase().contains(text.lowercase())) {
                filterdNames.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        customAdapter.filterList(filterdNames)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        itemsList.clear()
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        customAdapter = CustomAdapter(itemsList, CustomAdapter.OnClickListener { grad ->
            Toast.makeText(activity,grad[0],Toast.LENGTH_SHORT).show()
        })
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView?.layoutManager = layoutManager

        recyclerView?.adapter = customAdapter
        prepareItems()
    }

    private fun prepareItems(){
        val filename = "gradovi"

        if(File(activity?.filesDir?.path,filename).exists()){
            val fileInputStream: FileInputStream? = context?.openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                text?.let { itemsList.add(it.drop(1).dropLast(1).split(",")) }
            }
        }

    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }*/
}