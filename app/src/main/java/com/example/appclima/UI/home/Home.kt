package com.example.appclima.UI.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.appclima.R
import com.example.appclima.utils.constantes.constantes
import com.example.appclima.databinding.FragmentHomeBinding
import com.example.appclima.model.WeatherResponse
import com.example.appclima.repository.ApiClima
import com.example.appclima.model.remote.RetrofitClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    var adressLatlng: LatLng? = null
    private var lat = ""
    private var lon = ""
    private var city = ""
    private var location = ""
    private var json = Gson()
    var adrress = ""


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation!!
            lat = mLastLocation.latitude.toString()
            lon = mLastLocation.longitude.toString()



        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getLastLocation()
        binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun islocationEnable(): Boolean {

        var locationManager: LocationManager = requireContext().applicationContext.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (islocationEnable()) {

//                Toast.makeText(this, "Getting last location", Toast.LENGTH_LONG).show()

                mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                       /*
                       * binding.getclima.setOnClickListener {
                            val geoCoder = Geocoder(requireContext().applicationContext)

                            val adressList = geoCoder.getFromLocation(
                                location.latitude, location.longitude,
                                1
                            )

                            city = adressList[0].locality
                            adrress = adressList[0].getAddressLine(0)
                            lat = adressList[0].countryName
                            binding.temperatura.text = "${city + adrress}"


                        }
                       *
                       * */
                        lat = location.latitude.toString()
                        lon = location.longitude.toString()



                    }
                }
            } else {
                Toast.makeText(
                    requireContext().applicationContext,
                    "Turn on location",
                    Toast.LENGTH_LONG
                ).show()
                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(i)
            }
        } else {
            requestPermissions()

        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.myLooper()
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun getclima(latitud: String, longitud: String) {
          val retofil= RetrofitClient()
          val services=retofil.getClient(constantes.URL_BASE).create(ApiClima::class.java)
          val response=services.getClima(latitud, longitud, constantes.API_KEY).enqueue(object : Callback<WeatherResponse> {
              override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                  if (response.code() == 200){
                      val jsoninstallments = response.body()
                      val datos=jsoninstallments!!.main!!.weather.forEach {
                          it.main
                      val datos= jsoninstallments.main
                      val temperatura=datos!!.tem.toInt()
                      val amanecer= datos.sunrise
                      val temperatura_min= datos.temp_min
                      val humidity= datos.pressure
                      binding.temperatura.setText("temperatura en kelvin:$temperatura")
                      binding.descripcion.setText("$amanecer")
                      binding.humedad.setText("$humidity")
                      binding.visivilida.setText("temperatura minima en kelvin:$temperatura_min")
                      }

                      Log.d("responsiveJSON", "onResponse dtos: $response")

                  }
              }
              override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                  Log.d("responsiveJSON", "onResponse: ${t.message}")
              }
          })
    }

    private fun laodingImage(response: Response<JsonObject>) {
        TODO("Not yet implemented")
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_manu) {
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        val firebase = FirebaseAuth.getInstance()
        firebase.signOut()
        findNavController().navigate(R.id.login)
        requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
        Toast.makeText(requireContext(), "estas navegando", Toast.LENGTH_SHORT).show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

}