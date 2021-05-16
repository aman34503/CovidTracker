package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import kotlinx.android.synthetic.main.state_rv_item.view.*

class StateRVAdapter (private val stateList: List<StateModel>) :
    RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>() {
    class StateRVViewHolder(itemView: View) : ViewHolder(itemView) {
        val StateNameTV: TextView = itemView.findViewById(R.id.idTVStateCases)
        val casesTV: TextView = itemView.findViewById(R.id.idTVCases)
        val deathsTV: TextView = itemView.findViewById(R.id.idTVDeaths)
        val recoveredTV: TextView = itemView.findViewById(R.id.idTVRecovered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
        val stateData = stateList[position]
        holder.casesTV.text = stateData.Cases.toString()
        holder.StateNameTV.text = stateData.state
        holder.deathsTV.text = stateData.deaths.toString()
        holder.recoveredTV.text = stateData.recovered.toString()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}