package com.example.infobyte.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.infobyte.data.models.stocks
import com.example.infobyte.data.models.stocksItem
import com.example.infobyte.databinding.SingleBlockBinding

class stackadapter (val stocklist: ArrayList<stocksItem>): RecyclerView.Adapter<stackadapter.mycustomaddpter>() {
 class mycustomaddpter(val binding:SingleBlockBinding):RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mycustomaddpter {
      return mycustomaddpter(SingleBlockBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return stocklist.size

    }

    override fun onBindViewHolder(holder: mycustomaddpter, position: Int) {
        holder.binding.chamblfert.text= stocklist[position].NAME


    }
}