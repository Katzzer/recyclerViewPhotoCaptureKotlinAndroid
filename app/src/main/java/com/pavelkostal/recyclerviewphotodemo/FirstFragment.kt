package com.pavelkostal.recyclerviewphotodemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import java.io.ByteArrayOutputStream


class FirstFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 100
        const val CAMERA_REQUEST_CODE = 200
        private var i = 0
    }

    private lateinit var adapter: RecyclerAdapter
    private var listText = arrayListOf("McDonald's", "HP")
    private var listImages = arrayListOf("", "")
    private lateinit var imageView: PhotoView
    private var positionInAdapter:Int = 9999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewForTesting()
        imageView = view.findViewById(R.id.photoView)

        view.findViewById<Button>(R.id.cameraButton).setOnClickListener {
            capturePhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {

            adapter.notifyItemChanged(positionInAdapter)
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
            listImages[positionInAdapter] = bitMapToString(bitmap)

        }
    }

    private fun setRecyclerViewForTesting() {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager



        adapter = RecyclerAdapter(listText, listImages, object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                 // Listen your click event here
                capturePhoto().also { result ->
                    positionInAdapter = position
                }
            }
        }, this)
        recyclerView.adapter = adapter

    }


    private fun capturePhoto() {
        if (getCameraPermission(requireContext())) {
            val photo = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(photo, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    private fun getCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun bitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }

}
