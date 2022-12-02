package luky.zadanie.zadaniefinal.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.adapter.AddDeleteFriendAdapter
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentAddDeleteFriendBinding
import luky.zadanie.zadaniefinal.databinding.FragmentPubDetailBinding
import luky.zadanie.zadaniefinal.databinding.FragmentPubListBinding
import luky.zadanie.zadaniefinal.helper.logOut
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.AddDeleteFriendViewModel
import luky.zadanie.zadaniefinal.viewmodel.PubViewModel


@Suppress("DEPRECATION")
class AddDeleteFriendFragment : Fragment() {

    private var _binding: FragmentAddDeleteFriendBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddDeleteFriendViewModel
    private lateinit var recyclerView: RecyclerView

    private var isVisibleNav = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AddDeleteFriendViewModel(Repository.getInstance(ApiService.create(requireContext()),
        PubRoomDatabase.getDatabase(requireContext())))
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDeleteFriendBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelAddDelete = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFriend()

        recyclerView = binding.recyclerViewAddDelete

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

        viewModel.friends.observe(viewLifecycleOwner){ it1 ->
            if (it1!=null){
                recyclerView.adapter = AddDeleteFriendAdapter(it1,viewModel)
            }

        }

        binding.addFriendButton.setOnClickListener{
            if (binding.friendNameInput.text.isNullOrBlank()){
                Snackbar.make(
                    binding.root.rootView,
                    "Please add name of your friend",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            else{
                viewModel.addFriend(binding.friendNameInput.text.toString())
                viewModel.getFriend()
            }
        }

        binding.floatingActionButton.setOnClickListener {
            if (!isVisibleNav){
                binding.floatingActionButtonPubs.visibility = View.VISIBLE
                binding.floatingActionButtonNearPubs.visibility = View.VISIBLE
                binding.floatingActionButtonFriend.visibility = View.VISIBLE
                isVisibleNav = !isVisibleNav
            }
            else{
                binding.floatingActionButtonPubs.visibility = View.GONE
                binding.floatingActionButtonNearPubs.visibility = View.GONE
                binding.floatingActionButtonFriend.visibility = View.GONE
                isVisibleNav = !isVisibleNav
            }
        }

        binding.floatingActionButtonPubs.setOnClickListener{
            val action = AddDeleteFriendFragmentDirections.actionAddDeleteFriendFragmentToPubListFragment()
            view.findNavController().navigate(action)
        }

        binding.floatingActionButtonNearPubs.setOnClickListener {
            val action = AddDeleteFriendFragmentDirections.actionAddDeleteFriendFragmentToNearPubListFragment()
            view.findNavController().navigate(action)
        }

        binding.floatingActionButtonFriend.setOnClickListener {
            val action = AddDeleteFriendFragmentDirections.actionAddDeleteFriendFragmentToFriendFragment()
            view.findNavController().navigate(action)
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