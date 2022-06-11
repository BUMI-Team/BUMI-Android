package com.dicoding.android.bumi.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.databinding.FragmentAccountBinding
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.dicoding.android.bumi.utils.Constants

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null

    private lateinit var accViewModel: AccountViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        accViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        accViewModel.getUser(Constants.token)
        accViewModel.setUser().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    val tvName = binding.tvNameAcc
                    val tvEmail = binding.tvEmailAcc

                    tvName.setText(it.userRecord.displayName)
                    tvEmail.setText(it.userRecord.email)
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.btnEditProfil?.setOnClickListener {
            val intent = Intent (this@AccountFragment.context, EditAccountActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnConsultationAgenda?.setOnClickListener {
            val intent = Intent(this@AccountFragment.context, ConsultationAgendaActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnLogout?.setOnClickListener {
            val intent = Intent (activity, WelcomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {
        private const val TAG = "AccountFragment"
    }
}