package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetDetailImageCorousalAdapter
import com.yadav.pawdoption.databinding.FragmentPetDetailBinding
import me.relex.circleindicator.CircleIndicator

class PetDetailFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: PetDetailImageCorousalAdapter
    lateinit var indicator: CircleIndicator

    var _binding: FragmentPetDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPetDetailBinding.inflate(inflater, container, false)

        viewPager = binding.vpPetDetailsImage

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val images: MutableList<String> = mutableListOf()
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-14%20at%205.59.47%20PM.png?alt=media&token=7c161da6-dbe0-4885-8500-b77d24ea1114")
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-15%20at%206.07.25%20PM.png?alt=media&token=b6f38fa9-3144-4ebe-97f0-021c58a892b0")


        images?.let{
            viewPagerAdapter = PetDetailImageCorousalAdapter(requireContext(), it)
            viewPager.adapter = viewPagerAdapter
            indicator = requireView().findViewById(R.id.inPetDetailsImage) as CircleIndicator
            indicator.setViewPager(viewPager)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapPetDetailsShelterLocation) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(44.65423, -63.625859)
        val markerOptions = MarkerOptions().position(latLng).title("Shelter")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
    }

}