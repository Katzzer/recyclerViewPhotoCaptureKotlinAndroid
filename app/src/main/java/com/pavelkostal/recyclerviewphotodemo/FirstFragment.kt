package com.pavelkostal.recyclerviewphotodemo

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

//        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                result.data
//            }
//        }
//
//        startForResult.launch(Intent(activity, MainActivity::class.java))

        setRecyclerViewForTesting()
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



}