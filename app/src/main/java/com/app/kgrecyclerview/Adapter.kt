package com.app.kgrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class Adapter  : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val list = mutableListOf<String>()

    fun insert(list : List<String>){
        val oldSize = itemCount
        this.list.addAll(list)
        notifyItemRangeInserted(oldSize , itemCount -1)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val itemTxt: AppCompatTextView = view.findViewById(R.id.txtItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item ,parent, false))

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.itemTxt.text = item
    }

    override fun getItemCount() = list.size
}