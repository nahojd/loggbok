package se.uav.loggbok

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import se.uav.loggbok.home.HomeFragment
import se.uav.loggbok.log.LogFragment
import se.uav.loggbok.model.Trip

class MainActivity : AppCompatActivity(), LogFragment.OnListFragmentInteractionListener {
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
                Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show()
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
    }

    override fun onTripInteraction(trip: Trip?) {
        //Eventually we will show a detail view for a Trip here.
    }
}
