package com.example.eventdicodingapps.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicodingapps.MainViewModel
import com.example.eventdicodingapps.MainViewModelFactory
import com.example.eventdicodingapps.Result
import com.example.eventdicodingapps.adapter.AdapterEvent
//import com.example.eventdicodingapps.EventRepository
//import com.example.eventdicodingapps.MainViewModelFactory
//import com.example.eventdicodingapps.adapter.EventAdapter
//import com.example.eventdicodingapps.data.local.entity.DicodingEntity
//import com.example.eventdicodingapps.data.local.room.DicodingDatabase
import com.example.eventdicodingapps.databinding.FragmentHomeBinding
import com.example.eventdicodingapps.ui.setting.SettingViewModelFactory

class UpcomingFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var verticalAdapter: AdapterEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: MainViewModelFactory = MainViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        verticalAdapter = AdapterEvent { events ->
            if (events.isFavorite == true) {
                viewModel.deleteEvents(events)
            } else {
                viewModel.saveEvents(events)
            }
        }

        verticalAdapter.setLoadingState(true)

        viewModel.getUpcomingEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    verticalAdapter.setLoadingState(true)
                }

                is com.example.eventdicodingapps.Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    verticalAdapter.setLoadingState(false)
                    verticalAdapter.submitList(result.data)
                }

                is com.example.eventdicodingapps.Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "An error occurred" + result.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = verticalAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}