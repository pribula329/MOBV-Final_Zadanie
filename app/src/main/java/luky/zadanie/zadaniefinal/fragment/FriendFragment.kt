package luky.zadanie.zadaniefinal.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.adapter.FriendAdapter
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentFriendBinding
import luky.zadanie.zadaniefinal.helper.logOut
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.FriendViewModel


@Suppress("DEPRECATION")
class FriendFragment : Fragment() {

    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FriendViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = FriendViewModel(Repository.getInstance(ApiService.create(requireContext()),
            PubRoomDatabase.getDatabase(requireContext())))
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelFriend = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFriendWithPub()

        recyclerView = binding.recyclerViewFriend
        binding.recyclerViewFriend.layoutManager = LinearLayoutManager(this.context)

        viewModel.statusSucces.observe(viewLifecycleOwner){

            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()

        }

        viewModel.statusError.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()

        }

        viewModel.friendsWithPub.observe(viewLifecycleOwner){ it1 ->
            println(it1)
            if (it1!=null){
                recyclerView.adapter = FriendAdapter(it1,viewModel)
            }

        }
    }


    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.basic_menu, menu)",
        "luky.zadanie.zadaniefinal.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.basic_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionLogOut -> {
                view?.let { logOut(it,requireContext()) }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}