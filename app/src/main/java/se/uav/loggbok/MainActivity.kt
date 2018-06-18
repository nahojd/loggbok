package se.uav.loggbok

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import org.koin.android.architecture.ext.viewModel
import se.uav.loggbok.home.HomeFragment
import se.uav.loggbok.log.LogFragment
import se.uav.loggbok.model.Trip
import se.uav.loggbok.model.TripViewModel

private const val PERMISSIONS_REQUEST_LOCATION: Int = 1

class MainActivity : AppCompatActivity(), LogFragment.OnListFragmentInteractionListener {

    private val tripViewModel by viewModel<TripViewModel>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_log -> {
                val logFragment = LogFragment.newInstance(1)
                openFragment(logFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                toast("Not implemented (yet)")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                alert("This application needs location access in order to work", "Location access") {
                    yesButton {
                        requestLocationPermission()
                    }
                }.show()
            }
            else {
                requestLocationPermission()
            }
        }
        else {
            Log.d("MainActivity", "We already have location access")
            //TODO: Store that we have location access or something
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO: Store that we have location access or something
                } else {
                    alert("The application cannot function without location access", "Location access") {
                        yesButton {
                            finish()
                        }
                    }.show()
                }
                return

            }
            else -> {
                //Ignore all other requests
            }
        }
    }

    override fun onTripInteraction(trip: Trip?) {
        //Eventually we will show a detail view for a Trip here.
    }
}
