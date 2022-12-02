package luky.zadanie.zadaniefinal.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.card.MaterialCardView
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubNear
import luky.zadanie.zadaniefinal.fragment.PubListFragmentDirections
import luky.zadanie.zadaniefinal.helper.getIconPub
import luky.zadanie.zadaniefinal.viewmodel.NearPubViewModel
import kotlin.math.roundToInt

class NearPubAdapter (private val dataset: List<PubNear>, private val model: NearPubViewModel): RecyclerView.Adapter<NearPubAdapter.NearPubViewHolder>() {

    //class for ViewHolder
    class NearPubViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val viewPub: TextView = view.findViewById(R.id.pubNearTitle)
        val viewDistance: TextView = view.findViewById(R.id.pubNearDistance)
        val viewImage: ImageView = view.findViewById(R.id.pubNearImage)
        val viewCard: MaterialCardView = view.findViewById(R.id.pubNearCard)
        val viewCheckIn: LottieAnimationView = view.findViewById(R.id.animationCheckView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearPubViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.near_pub_item, parent, false)

        return NearPubViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NearPubViewHolder, position: Int) {
        val item = dataset[position]
        holder.viewPub.text = item.nearName
        holder.viewDistance.text= " ${item.distance.roundToInt().toString()} metres"
        holder.viewImage.setImageResource(getIconPub(item.nearType))
        if (model.myPub.value?.nearId==item.nearId){
            holder.viewCard.setBackgroundColor(Color.parseColor("#FF03DAC5"))
            println(item.nearName)
        }

        holder.viewCheckIn.setOnClickListener {

            model.checkMeToPub(item)

        }


    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}