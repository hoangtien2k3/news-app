package com.hoangtien2k3.news_app.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoangtien2k3.news_app.databinding.FragmentQrCodePayBinding

class QrCodePayFragment : Fragment() {
    private lateinit var binding: FragmentQrCodePayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrCodePayBinding.inflate(inflater, container, false)
        binding.back.setOnClickListener { requireActivity().onBackPressed() }
        return binding.root
    }
}