package se.uav.loggbok.home


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import se.uav.loggbok.R
import se.uav.loggbok.model.TripViewModel


class HomeFragment : Fragment() {

    private lateinit var tripViewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripViewModel = ViewModelProviders.of(activity!!).get(TripViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        //TODO: Varför funkar inte Kontlins synthetic-import?
        val startButton = rootView.findViewById<Button>(R.id.start_button)
        val stopButton = rootView.findViewById<Button>(R.id.stop_button)
        val textTimeElapsed = rootView.findViewById<TextView>(R.id.text_time_elapsed)



        tripViewModel.elapsedTime.observe(activity!!, Observer {
            if (it != null)
                textTimeElapsed.text = String.format("%d:%02d:%02d", it / 3600, (it % 3600) / 60, (it % 60))
        })



        startButton.setOnClickListener {
            tripViewModel.start()
            textTimeElapsed.text = "0:00:00"
            startButton.visibility = View.GONE
            stopButton.visibility = View.VISIBLE
        }

        stopButton.setOnClickListener {
            tripViewModel.stop()
            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE

            Toast.makeText(context, "Trip complete!", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
    
}
