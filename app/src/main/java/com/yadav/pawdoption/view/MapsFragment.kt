package com.yadav.pawdoption.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yadav.pawdoption.R
import com.yadav.pawdoption.persistence.SheltersDAO
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : Fragment() {

    private val sheltersDAO = SheltersDAO()
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    private val callback = OnMapReadyCallback { googleMap ->

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        sheltersDAO.getShelters().observe(viewLifecycleOwner) {
            var nearbyShelters: ArrayList<LatLng> = arrayListOf()
            var keys = it.keys
            for (key in keys) {
                var lat = it.get(key)!!.latitude
                var long = it.get(key)!!.longitude

                var distance: FloatArray = FloatArray(100)

                if (lat != null && long != null) {
                    Location.distanceBetween(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        lat,
                        long,
                        distance
                    )

                    if (distance[0] < 10000) {
                        nearbyShelters.add(LatLng(lat, long))
                    }
                }
            }

            for (point in nearbyShelters) {
                var mo = MarkerOptions().position(point).title("Here").icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                googleMap.addMarker(mo)
            }

        }

        val markerOptions = MarkerOptions().position(latLng).title("Current location")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fetchLocation()
        return view
    }


    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if ((ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    this.requireContext(),
                    currentLocation.latitude.toString() + "" + currentLocation.longitude.toString(),
                    Toast.LENGTH_SHORT
                ).show()


                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.mapShelterList) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {


        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }
}
