package com.pavelkostal.recyclerviewphotodemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 100
        const val CAMERA_REQUEST_CODE = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonCapturePhoto = view.findViewById<Button>(R.id.button)
        buttonCapturePhoto.setOnClickListener {
            capturePhoto()
        }
        setRecyclerViewForTesting()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            print("photo captured")
        }
    }

    private fun setRecyclerViewForTesting() {
        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        val list = listOf("item1", "item2")

        val adapter = RecyclerAdapter(list, object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // Listen your click event here
                capturePhoto().also { result ->
                    // do something

                    // dont forget to call notifyItem if you want to update an item in
                    // RecyclerView
//                    adapter.notifyItemChanged(position)
                }
            }
        })
        recyclerView.adapter = adapter

    }

//    fun capturePhoto() {
//        if (getCameraPermission(requireContext())) {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(requireActivity(), cameraIntent, CAMERA_REQUEST_CODE, null)
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
//        }
//    }

    private fun capturePhoto() {
        if (getCameraPermission(requireContext())) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    private fun getCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

}
