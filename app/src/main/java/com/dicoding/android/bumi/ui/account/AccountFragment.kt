package com.dicoding.android.bumi.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.databinding.FragmentAccountBinding
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var accViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
//        setupAction()
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        _binding?.btnEditProfil?.setOnClickListener {
            val intent = Intent (this@AccountFragment.context, EditAccountActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnConsultationAgenda?.setOnClickListener {
            val intent = Intent(this@AccountFragment.context, ConsultationAgendaActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnLogout?.setOnClickListener {
            val intent = Intent (this@AccountFragment.context, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Logout
    private fun setupAction() {
        binding.btnLogout.setOnClickListener {
            accViewModel.logout()
        }
    }
}