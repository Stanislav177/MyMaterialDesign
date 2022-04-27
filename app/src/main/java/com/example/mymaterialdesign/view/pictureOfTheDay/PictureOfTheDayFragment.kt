package com.example.mymaterialdesign.view.pictureOfTheDay

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentPictureOfTheDayBinding
import com.example.mymaterialdesign.view.settings.SettingFragment
import com.example.mymaterialdesign.view.viewpager.TO_DAY
import com.example.mymaterialdesign.view.viewpager.ViewPagerPictureAdapter
import com.example.mymaterialdesign.view.viewpager.YESTERDAY
import com.example.mymaterialdesign.viewModel.PictureViewModel

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() {
            return _binding!!
        }

    private var day: Int = 0

    private val liveData: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestAPI()
        initPagerViewPicture()
        binding.tabsPicture.setupWithViewPager(binding.viewPagerPicture)
        binding.tabsPicture.getTabAt(TO_DAY)?.setIcon(R.drawable.ic_image)
        binding.tabsPicture.getTabAt(YESTERDAY)?.setIcon(R.drawable.ic_image_yesterday)
    }

    private fun initPagerViewPicture() {
        binding.viewPagerPicture.adapter =
            ViewPagerPictureAdapter(requireActivity().supportFragmentManager)

        binding.viewPagerPicture.addOnAdapterChangeListener(
            object : ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onAdapterChanged(
                    viewPager: ViewPager,
                    oldAdapter: PagerAdapter?,
                    newAdapter: PagerAdapter?
                ) {
                }

            }
        )
    }

    private fun requestAPI() {
        liveData.modDateDay(day)
        liveData.request()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(context, "Setting", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, SettingFragment.newInstance())
                    .addToBackStack(" ").commit()
            }
            android.R.id.home -> {
                Toast.makeText(context, "Home", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}