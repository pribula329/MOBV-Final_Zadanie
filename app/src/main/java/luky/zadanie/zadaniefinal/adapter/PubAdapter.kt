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
import luky.zadanie.zadaniefinal.fragment.PubListFragmentDirections
import luky.zadanie.zadaniefinal.helper.getIconPub

class PubAdapter (private val dataset: List<Pub>): RecyclerView.Adapter<PubAdapter.PubViewHolder>() {

    //class for ViewHolder
    class PubViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewPub: TextView = view.findViewById(R.id.pubTitle)
        val viewCount: TextView = view.findViewById(R.id.pubCount)
        val viewImage: ImageView = view.findViewById(R.id.pubImage)
        val viewCard: MaterialCardView = view.findViewById(R.id.pubCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.pub_item, parent, false)

        return PubViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: PubViewHolder, position: Int) {
        val item = dataset[position]
        holder.viewPub.text = item.pubName
        holder.viewCount.text= " ${item.usersCount.toString()} "
        holder.viewImage.setImageResource(getIconPub(item.pubType))

        holder.viewCard.setOnClickListener {
            val action = PubListFragmentDirections.actionPubListFragmentToPubDetailFragment(
                item.pubId,
                item.pubName,
                item.pubType,
                item.lat.toFloat(),
                item.lon.toFloat(),
                item.usersCount
            )
            holder.view.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }



}