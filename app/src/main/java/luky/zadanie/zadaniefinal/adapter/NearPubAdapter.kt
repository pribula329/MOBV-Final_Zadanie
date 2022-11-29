package luky.zadanie.zadaniefinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubNear
import luky.zadanie.zadaniefinal.fragment.PubListFragmentDirections
import luky.zadanie.zadaniefinal.helper.getIconPub
import kotlin.math.roundToInt

class NearPubAdapter (private val dataset: List<PubNear>): RecyclerView.Adapter<NearPubAdapter.NearPubViewHolder>() {

    //class for ViewHolder
    class NearPubViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewPub: TextView = view.findViewById(R.id.pubNearTitle)
        val viewDistance: TextView = view.findViewById(R.id.pubNearDistance)
        val viewImage: ImageView = view.findViewById(R.id.pubNearImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearPubViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.near_pub_item, parent, false)

        return NearPubViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: NearPubViewHolder, position: Int) {
        val item = dataset[position]
        holder.viewPub.text = item.nearName
        holder.viewDistance.text= " ${item.distance.roundToInt().toString()} metres"
        holder.viewImage.setImageResource(getIconPub(item.nearType))


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}