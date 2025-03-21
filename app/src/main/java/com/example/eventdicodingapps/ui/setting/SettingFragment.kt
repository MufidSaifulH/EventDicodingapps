package com.example.eventdicodingapps.ui.setting

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.eventdicodingapps.ReminderScheduler
import com.example.eventdicodingapps.databinding.FragmentSettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingViewModel: SettingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val pref = SettingPreference.getInstance(requireContext().dataStore)
        val factory = SettingViewModelFactory(pref) // Pakai factory yang benar
        settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observasi perubahan tema
        settingViewModel.getThemeSetting().asLiveData().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
        settingViewModel.getNotificationSetting().asLiveData().observe(viewLifecycleOwner) { isNotificationActive ->
            if (isNotificationActive) {
                Toast.makeText(requireContext(), "Notification is Activated", Toast.LENGTH_SHORT).show()
                binding.switchNotification.isChecked = true
            } else {
                Toast.makeText(requireContext(), "Notification is Deactivated", Toast.LENGTH_SHORT).show()
                binding.switchNotification.isChecked = false
            }
        }

        // Ketika switch diubah
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                settingViewModel.saveThemeSetting(isChecked)
            }
        }
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                settingViewModel.saveNotificationSetting(isChecked)
            }
            if (isChecked) {
                ReminderScheduler.scheduleDailyReminder(requireContext())
            } else {
                ReminderScheduler.cancelDailyReminder(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}