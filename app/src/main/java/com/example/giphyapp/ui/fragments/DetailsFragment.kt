package com.example.giphyapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.giphyapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(GIF_URL) }?.apply {
            val gifUrl = getString(GIF_URL)
            Glide.with(requireContext()).load(gifUrl).into(binding.detailsImageView)
        }
    }

    companion object {
        private const val GIF_URL = "GIF_URL"
        fun newInstance(imageUrl: String): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundleOf(GIF_URL to imageUrl)
            return fragment
        }
    }
}