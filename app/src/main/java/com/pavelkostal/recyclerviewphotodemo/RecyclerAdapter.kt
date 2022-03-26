package com.pavelkostal.recyclerviewphotodemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(private val list:List<String>, val listener: OnItemClickListener):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_firma_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.textView1.text = list[position]
        val item = list[position]
        // bind here
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var textView1: TextView = itemView.findViewById(R.id.firma_textView1)

        // create function bind instead of using init block
        fun bind(item:String){
            val textView1: TextView = itemView.findViewById(R.id.firma_textView1)
            // if you want to change image in your ImageView , you could also pass
            // your ImageView too
            textView1.setOnClickListener { view ->
                // this is just an example , but you get the idea

                // listen click event and pass view and position
                listener.onItemClick(view, adapterPosition)
            }
        }
    }

}