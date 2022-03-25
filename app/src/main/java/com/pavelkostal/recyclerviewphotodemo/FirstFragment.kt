package com.pavelkostal.recyclerviewphotodemo

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pavelkostal.recyclerviewphotodemo.databinding.FragmentFirstBinding

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
        var buttonCapturePhoto = view.findViewById<Button>(R.id.button)
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

        recyclerView?.let {
            recyclerView.layoutManager = layoutManager

            val adapter = RecyclerAdapterFirma(requireContext(), requireActivity())
            recyclerView.adapter = adapter
        }
    }

    private fun capturePhoto() {
        if (getCameraPermission(requireContext())) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    private fun getCameraPermission (context: Context):Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }



}