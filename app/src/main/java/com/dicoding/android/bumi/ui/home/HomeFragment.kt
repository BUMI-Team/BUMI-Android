package com.dicoding.android.bumi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.adapter.ListVideoAdapter
import com.dicoding.android.bumi.databinding.FragmentHomeBinding
import com.dicoding.android.bumi.ui.account.AccountViewModel
import com.dicoding.android.bumi.utils.Constants

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: ListVideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val root: View = binding.root
        binding.svSearchVideo.queryHint = getString(R.string.searchHint)
        binding.svSearchVideo.setIconifiedByDefault(false)
        setBannerSlider()
        setSpinner()
        adapter = ListVideoAdapter()
        homeViewModel.setVideo().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
        return root
    }

    private fun setupListVideo(genre : String) {
        homeViewModel.getListHomeVideo(genre)
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
                var selectedCategory : String
                selectedCategory = selectedItem as String
                setupListVideo("Kuliner")
                Toast.makeText(activity, selectedCategory, Toast.LENGTH_SHORT).show()
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
}