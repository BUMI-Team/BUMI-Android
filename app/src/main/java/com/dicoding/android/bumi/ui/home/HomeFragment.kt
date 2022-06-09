package com.dicoding.android.bumi.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.data.local.datastore.LoginPreferences
import com.dicoding.android.bumi.databinding.FragmentHomeBinding
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.dicoding.android.bumi.utils.Constants
import com.dicoding.android.bumi.utils.PrefViewModelFactory

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
//    private val storyViewModel: HomeViewModel by viewModels {
//        ViewModelFactory(this)
//    }
    private lateinit var homeViewModel: HomeViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.svSearchVideo.queryHint = getString(R.string.searchHint)
        binding.svSearchVideo.setIconifiedByDefault(false)
        setBannerSlider()
        setSpinner()
//        setupViewModel()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSpinner() {
        val spinner: Spinner = binding.categorySpinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.kategori_dropdown_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_list)
                spinner.adapter = adapter
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position)
                Toast.makeText(activity, "$selectedItem Selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun setBannerSlider() {

        val bannerSlider = binding.homeBannerSlider
        val bannerList = ArrayList<SlideModel>()

        bannerList.add(SlideModel(R.drawable.banner_example))
        bannerList.add(SlideModel(R.drawable.banner_example2))
        bannerList.add(SlideModel(R.drawable.banner_example3))
        bannerList.add(SlideModel(R.drawable.banner_example))

        bannerSlider.setImageList(bannerList, ScaleTypes.FIT)
    }

//    private fun setupViewModel() {
//        var name: String
//
//        homeViewModel = ViewModelProvider(this, PrefViewModelFactory(LoginPreferences.getInstance(dataStore)))[HomeViewModel::class.java]
//        homeViewModel.getUser().observe(viewLifecycleOwner) { user ->
//            if (user.isLogin) {
//                Constants.token = " ${user.token}"
//                name = user.name
//                binding.apply {
//                    listTrainingVideo.layoutManager = LinearLayoutManager(context)
//                    getToken(Constants.token)
//                }
//            } else {
//                val intent = Intent (activity, WelcomeActivity::class.java)
//                activity?.startActivity(intent)
//                activity?.finish()
//            }
//        }
//    }



//    private fun getToken(token: String) {
//        binding.apply {
//            if (token.isEmpty()) return
//            onLoading(true)
////            mainViewModel.setListStory(token)
//        }
//    }

//     Loading
    private fun onLoading(data: Boolean) {
        val visibilityState = if(data) View.VISIBLE else View.INVISIBLE
//        binding.progressBar.visibility = visibilityState
//        binding.tvMsg.visibility = visibilityState
    }
}