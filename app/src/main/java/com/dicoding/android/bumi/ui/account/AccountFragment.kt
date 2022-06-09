package com.dicoding.android.bumi.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.android.bumi.MainViewModel
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.databinding.FragmentAccountBinding
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.dicoding.android.bumi.utils.Constants
import com.dicoding.android.bumi.utils.PrefViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null

    // gatau pake yg mana masi coba2
    private val viewModel: AccountViewModel by viewModel()
    private lateinit var accViewModel: AccountViewModel

//    val pref = requireContext().dataStore


    private val binding get() = _binding!!
//    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        val uid = activity?.intent?.getStringExtra(Constants.EXTRA_UID)

        val mBundle = Bundle()
        mBundle.putString(Constants.EXTRA_UID, uid)

//         Account ViewModel
        accViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        if (uid != null) {
//            onLoading(true)
            accViewModel.setUser(Constants.token, uid)
        }
        accViewModel.getUser().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    val tv = TextView(activity)

                    val tvName = tv.findViewById<Button>(R.id.tv_name_acc)
                    val tvEmail = tv.findViewById<Button>(R.id.tv_email)

                    tvName.text = it.userRecord?.displayName
                    tvEmail.text = it.userRecord?.email
                }
//                onLoading(false)
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

//        accViewModel = ViewModelProvider(
//            this,
//            PrefViewModelFactory(LoginPreferences.getInstance(pref))
//        )[AccountViewModel::class.java]
        //        accViewModel =
//            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[AccountViewModel::class.java]

//        auth = Firebase.auth
//        val firebaseUser = auth.currentUser
//
//        if (firebaseUser == null) {
//            // Not signed in, launch the Login activity
//            val intent = Intent (this@AccountFragment.context, WelcomeActivity::class.java)
//            startActivity(intent)
//            return
//        }
//
//        _binding?.btnLogout?.setOnClickListener {
//            auth = FirebaseAuth.getInstance()
//            auth.signOut()
////            accViewModel.logout()
//            val intent = Intent (this@AccountFragment.context, WelcomeActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//            Toast.makeText(activity, "Test", Toast.LENGTH_SHORT).show()
//        }

        _binding?.btnEditProfil?.setOnClickListener {
            val intent = Intent (this@AccountFragment.context, EditAccountActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnConsultationAgenda?.setOnClickListener {
            val intent = Intent(this@AccountFragment.context, ConsultationAgendaActivity::class.java)
            startActivity(intent)
        }

        _binding?.btnLogout?.setOnClickListener {
//             auth.signOut()
//            accViewModel.logout()
            val intent = Intent (activity, WelcomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(activity, "Test", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }
    }
}