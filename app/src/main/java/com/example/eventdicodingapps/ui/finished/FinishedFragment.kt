package com.example.eventdicodingapps.ui.finished

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
import com.example.eventdicodingapps.databinding.FragmentDashboardBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var verticalAdapter: AdapterEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MainViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        verticalAdapter = AdapterEvent {
            if (it.isFavorite == true) {
                viewModel.deleteEvents(it)
            } else {
                viewModel.saveEvents(it)
            }
        }

        verticalAdapter.setLoadingState(true)

        viewModel.getFinishedEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    verticalAdapter.setLoadingState(false)
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    verticalAdapter.setLoadingState(false)
                    verticalAdapter.submitList(result.data)
                }

                is Result.Error -> {
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

        setupSearchView()
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                searchBar.setText(searchView.text)
                searchView.hide()
                viewModel.searchFinishedEvents(query).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            updateUI(isLoading = true, isEmpty = false)
                        }

                        is Result.Success -> {
                            verticalAdapter.submitList(result.data)
                            updateUI(isLoading = false, isEmpty = result.data.isEmpty())
                        }

                        is Result.Error -> {
                            updateUI(isLoading = false, isEmpty = true)
                        }
                    }
                }
                false
            }
        }
    }

    private fun updateUI(isLoading: Boolean, isEmpty: Boolean) {
        with(binding) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (!isEmpty && !isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}