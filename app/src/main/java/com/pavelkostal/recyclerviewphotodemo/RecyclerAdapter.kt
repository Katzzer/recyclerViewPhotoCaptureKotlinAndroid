package com.pavelkostal.recyclerviewphotodemo

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class RecyclerAdapter(private val list: List<String>, private val listImages: ArrayList<String>, val listener: OnItemClickListener, private val fragment: Fragment):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.textView1.text = list[position]

        if (listImages[position].isNotEmpty()) {
            Glide.with(fragment)
                .load(Base64.decode(listImages[position], Base64.NO_WRAP))
                .error(R.drawable.broken_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.image)
        }

        val item = list[position]
        // bind here
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var textView1: TextView = itemView.findViewById(R.id.firma_textView1)
        var image: ImageView = itemView.findViewById(R.id.image)

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