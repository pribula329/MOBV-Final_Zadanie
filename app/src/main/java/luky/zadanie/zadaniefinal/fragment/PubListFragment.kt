package luky.zadanie.zadaniefinal.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.PreferenceData
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.adapter.PubAdapter
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentPubListBinding
import luky.zadanie.zadaniefinal.helper.checkPermissions
import luky.zadanie.zadaniefinal.helper.logOut
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.PubViewModel



@Suppress("DEPRECATION")
class PubListFragment : Fragment() {


    private var _binding: FragmentPubListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PubViewModel
    private lateinit var recyclerView: RecyclerView

    private var isSortedMenu = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PubViewModel(Repository.getInstance(ApiService.create(requireContext()),
            PubRoomDatabase.getDatabase(requireContext())))
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding= FragmentPubListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelPubList = viewModel
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addPubsToDatabase()

        viewModel.status.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()

        }


        recyclerView = binding.recyclerView

        viewModel.allPubs.observe(viewLifecycleOwner){
                recyclerView.adapter = PubAdapter(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.swipRrefresh.setOnRefreshListener {
            binding.swipRrefresh.isRefreshing = false
            viewModel.addPubsToDatabase()
            viewModel.allPubs.observe(viewLifecycleOwner){
                recyclerView.adapter = PubAdapter(it)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        }



    }


    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.layout_menu, menu)",
        "luky.zadanie.zadaniefinal.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSortMenuByName -> {
                isSortedMenu = !isSortedMenu
                sortListByName()
                return true
            }
            R.id.actionSortMenuByPeople ->{
                isSortedMenu = !isSortedMenu
                sortListByPeople()
                return true
            }
            R.id.actionLogOut -> {
                view?.let { logOut(it,requireContext()) }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortListByName() {
        val sortDataset: LiveData<List<Pub>> = viewModel.allPubs
        if (isSortedMenu) {
            recyclerView.adapter = PubAdapter(sortDataset.value!!.sortedBy { it.pubName })
        } else {
            recyclerView.adapter = PubAdapter(sortDataset.value!!.sortedByDescending { it.pubName })
        }

    }

    private fun sortListByPeople() {
        val sortDataset: LiveData<List<Pub>> = viewModel.allPubs
        if (isSortedMenu) {
            recyclerView.adapter = PubAdapter(sortDataset.value!!.sortedBy { it.usersCount })
        } else {
            recyclerView.adapter = PubAdapter(sortDataset.value!!.sortedByDescending { it.usersCount})
        }

    }


}