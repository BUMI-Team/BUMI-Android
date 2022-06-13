package com.dicoding.android.bumi.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.adapter.ListVideoAdapter
import com.dicoding.android.bumi.data.model.ListVideosItem
import com.dicoding.android.bumi.databinding.FragmentHomeBinding
import com.dicoding.android.bumi.ui.account.AccountViewModel
import com.dicoding.android.bumi.ui.detail.DetailVideoActivity
import com.dicoding.android.bumi.ui.welcome.WelcomeActivity
import com.dicoding.android.bumi.utils.Constants
import java.lang.StringBuilder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: ListVideoAdapter
    private val idWatchHistory = StringBuilder()

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
//        val sharedPrefsWatchHistory =
//            activity?.getSharedPreferences("sharedPrefsWatchHistory", Context.MODE_PRIVATE)
//        setBannerSlider()
//        setSpinner()
        adapter = ListVideoAdapter()
        adapter.SetOnItemClickCallback(object : ListVideoAdapter.OnItemClickCallback {
            override fun ItemClicked(videoData: ListVideosItem) {
//                idWatchHistory.append(videoData.noID.toString())
                Constants.EXTRA_WATCH_HISTORY_ID = videoData.noID
                Constants.EXTRA_VIDEO_ID = videoData.id
                Constants.EXTRA_VIDEO_TITLE = videoData.title
                Constants.EXTRA_VIDEO_DESC = videoData.description
                Constants.EXTRA_VIDEO_INDEX_ID = videoData.noID.toString()
//                Intent(activity, DetailVideoActivity::class.java).also {
//                    startActivity(it)
//                }
                val intent = Intent(activity, DetailVideoActivity::class.java)
                startActivity(intent)
            }
        })
//        val editor = sharedPrefsWatchHistory?.edit()
//        editor?.apply {
//            putString("ID_WATCH_HISTORY", idWatchHistory.toString())
//        }?.apply()
//        Constants.EXTRA_WATCH_HISTORY_ID = idWatchHistory.toString()
        Toast.makeText(activity, "${Constants.EXTRA_WATCH_HISTORY_ID}", Toast.LENGTH_SHORT).show()
        binding.apply {
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                listTrainingVideo.layoutManager = GridLayoutManager(activity, 2)
            } else {
                listTrainingVideo.layoutManager = LinearLayoutManager(activity)
            }
            listTrainingVideo.adapter = adapter

        setWatchHistory()
            setBannerSlider()
            setSpinner()
        }
        homeViewModel.setVideo().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
        return root
    }

    private fun setWatchHistory() {
//        val sharedPrefsWatchHistory =
//            activity?.getSharedPreferences("sharedPrefsWatchHistory", Context.MODE_PRIVATE)
//        Constants.EXTRA_WATCH_HISTORY_ID = idWatchHistory.toString()
        Toast.makeText(activity, "${Constants.EXTRA_WATCH_HISTORY_ID}", Toast.LENGTH_SHORT).show()
    }

    private fun setupListVideo(genre: String) {
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position)
                var selectedCategory: String
                selectedCategory = selectedItem as String
                setupListVideo(selectedCategory)
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