package com.pavelkostal.recyclerviewphotodemo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapterFirma(val context: Context, val activity: Activity):RecyclerView.Adapter<RecyclerAdapterFirma.ViewHolder>() {

    private val list1 = listOf("AAA1", "BBB1")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterFirma.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_firma_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterFirma.ViewHolder, position: Int) {
        holder.textView1.text = list1[position]
    }

    override fun getItemCount(): Int {
        return list1.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var textView1: TextView = itemView.findViewById(R.id.firma_textView1)

        init {
            textView1.setOnClickListener {
                capturePhoto(context, activity)
            }
        }
    }

    fun capturePhoto(context: Context, activity: Activity) {
        if (getCameraPermission(context)) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(activity, cameraIntent, FirstFragment.CAMERA_REQUEST_CODE, null)
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), FirstFragment.CAMERA_REQUEST_CODE)
        }
    }

    private fun getCameraPermission (context: Context):Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }



}