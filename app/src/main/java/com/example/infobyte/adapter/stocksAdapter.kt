package com.example.infobyte.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.infobyte.data.models.stocksItem
import com.example.infobyte.databinding.StockItemBinding

class stocksAdapter : RecyclerView.Adapter<stocksAdapter.stocksViewHolder>() {

    inner class stocksViewHolder(val binding : StockItemBinding) : ViewHolder(binding.root)
    private val differCallBack = object : DiffUtil.ItemCallback<stocksItem>(){
        override fun areItemsTheSame(oldItem: stocksItem, newItem: stocksItem): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: stocksItem, newItem: stocksItem): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): stocksViewHolder {
        return stocksViewHolder(StockItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: stocksViewHolder, position: Int) {
        val currentItem=differ.currentList[position]
        holder.binding.apply {
            stockName.text=currentItem.NAME.toString()
            stockpercecntage.text=currentItem.Perc_change.toString()+"%"
            stockprice.text=currentItem.ChangeInPRICE.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}