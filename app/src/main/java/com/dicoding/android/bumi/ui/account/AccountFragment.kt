package com.dicoding.android.bumi.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.databinding.FragmentAccountBinding
import com.dicoding.android.bumi.ui.consultation.ConsultationViewModel
import kotlinx.coroutines.Dispatchers.Main

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[ConsultationViewModel::class.java]

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root

        setupAction()
    }

    private fun setupAction() {
        _binding?.btnEditProfil?.setOnClickListener {
            Toast.makeText(activity,"Text!",Toast.LENGTH_SHORT).show();
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        _binding?.btnEditProfil?.setOnClickListener {
            val intent = Intent (this@AccountFragment.context, EditAccountActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}