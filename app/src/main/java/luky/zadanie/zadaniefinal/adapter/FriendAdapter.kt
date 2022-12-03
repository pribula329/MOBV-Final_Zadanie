package luky.zadanie.zadaniefinal.adapter

import android.util.Half.toFloat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.fragment.FriendFragmentDirections
import luky.zadanie.zadaniefinal.network.FriendWithPubData

class FriendAdapter(private val dataset: List<FriendWithPubData>): RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val viewNameFriend: TextView = view.findViewById(R.id.friendwithPubNameItem)
        val viewPubName: TextView = view.findViewById(R.id.PubNameItem)
        val viewPubIcon: ImageView = view.findViewById(R.id.friendWithPupIcon)
        val viewCard: MaterialCardView = view.findViewById(R.id.friendWithPubCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val item = dataset[position]
        holder.viewNameFriend.text = item.nameFriend
        if (item.namePubFriend!=null){
            holder.viewPubName.text = item.namePubFriend
        }
        else{
            holder.viewPubName.text = "I am home"
            holder.viewPubIcon.setImageResource(R.drawable.baseline_close_24)
        }

        holder.viewCard.setOnClickListener {
            if (item.namePubFriend != null) {
                val action = FriendFragmentDirections.actionFriendFragmentToPubDetailFragment(
                    item.idPubFriend!!,
                    item.namePubFriend!!,
                    "",
                    item.latPubFriend!!.toFloat(),
                    item.lonPubFriend!!.toFloat(),
                    1
                )
                holder.view.findNavController().navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}