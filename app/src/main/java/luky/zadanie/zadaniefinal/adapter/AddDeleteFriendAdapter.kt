package luky.zadanie.zadaniefinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.network.FriendData
import luky.zadanie.zadaniefinal.viewmodel.AddDeleteFriendViewModel

class AddDeleteFriendAdapter(private val dataset: List<FriendData>, private val model: AddDeleteFriendViewModel): RecyclerView.Adapter<AddDeleteFriendAdapter.AddDeleteFriendViewHolder>() {

    class AddDeleteFriendViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val viewButton: Button = view.findViewById(R.id.deleteFriendButton)
        val viewNameFriend: TextView = view.findViewById(R.id.friendNameItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddDeleteFriendViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_delete_item, parent, false)
        return AddDeleteFriendViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: AddDeleteFriendViewHolder, position: Int) {
        val item = dataset[position]
        holder.viewNameFriend.text = item.nameFriend.toString()
        holder.viewButton.setOnClickListener {
            model.deleteFriend(item.nameFriend)
            model.getFriend()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}