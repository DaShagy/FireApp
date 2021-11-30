package com.example.fireapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fireapp.databinding.FragmentFirestoreBinding
import kotlinx.android.synthetic.main.fragment_firestore.view.*

class FirestoreFragment : Fragment() {
    private var _binding : FragmentFirestoreBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFirestoreBinding.inflate(layoutInflater)
        binding.root.textViewFirestore.text = "Dans la capi comme Messi"
        return binding.root
    }
}