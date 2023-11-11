package com.example.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.OptionFragment
import com.example.android.navigation.R


internal class CustomAdapter(private var itemsList: List<List<String>>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var grad: TextView = view.findViewById(R.id.grad)
        var koordinate: TextView = view.findViewById(R.id.koordinate)
        val startButton: Button = itemView.findViewById(R.id.start_button)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.grad.text = item[0]
        holder.koordinate.text = "(" + item[1] + ", " + item[2] + ")"
        val sbutton = holder.startButton

        sbutton.setOnClickListener{ v ->
            v.findNavController().navigate(R.id.action_titleFragment_to_mapFragment)
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

        sbutton.isEnabled = true

        holder.itemView.setOnClickListener { v ->
            val activity = v!!.context as AppCompatActivity
            val optionFragment = OptionFragment()
            activity.supportFragmentManager.beginTransaction().add(R.id.rec, optionFragment)
                .addToBackStack(null).commit()
        }
    }
    override fun getItemCount(): Int {
        return itemsList.size
    }
    class OnClickListener(val clickListener: (grad: List<String>) -> Unit) {
        fun onClick(grad: List<String>) = clickListener(grad)
    }
    fun filterList(superHeroNames: ArrayList<List<String>>) {
        this.itemsList = superHeroNames
        notifyDataSetChanged()
    }
}