package com.example.appclima.UI.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.appclima.R
import com.example.appclima.databinding.FragmentDetailsNotasBinding


class DetailsNotas : Fragment(R.layout.fragment_details_notas) {

    private lateinit var binding: FragmentDetailsNotasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsNotasBinding.bind(view)




    }
}