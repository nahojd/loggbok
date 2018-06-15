package se.uav.loggbok.log

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_trip.view.*
import se.uav.loggbok.R

import se.uav.loggbok.log.LogFragment.OnListFragmentInteractionListener
import se.uav.loggbok.model.Trip
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * [RecyclerView.Adapter] that can display a [Trip] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class TripRecyclerViewAdapter(
        private val mValues: List<Trip>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<TripRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Trip
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onTripInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_trip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.titleView.text = item.name
        holder.timeView.text = "${item.startTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))} - ${item.endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))}"

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.trip_title
        val timeView: TextView = view.trip_time

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }
}
