package com.example.pigdice

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class LeaderboardAdapter( private var leaderboardList: List<LeaderboardItem>):
    RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder>(){
        internal inner class MyViewHolder( view: View) : RecyclerView.ViewHolder(view){
            var item : TextView = view.findViewById(R.id.leaderboardItemText)
        }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from( parent.context ).inflate(R.layout.leaderboard_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val leaderboardItem = leaderboardList[position]
        holder.item.text = leaderboardItem.toString()
        if (leaderboardItem.winnerName == "Computer"){
            holder.item.setTextColor(Color.RED)
        } else {
            holder.item.setTextColor(Color.BLUE)
        }

    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }
}