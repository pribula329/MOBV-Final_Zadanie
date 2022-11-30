package luky.zadanie.zadaniefinal.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.adapter.NearPubAdapter
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentNearPubListBinding
import luky.zadanie.zadaniefinal.helper.logOut
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.NearPubViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NearPubListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NearPubListFragment : Fragment() {

    private var _binding: FragmentNearPubListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NearPubViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var myFusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {

                // Only approximate location access granted.
            }
            else -> {

                // No location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = NearPubViewModel(Repository.getInstance(ApiService.create(requireContext()),
            PubRoomDatabase.getDatabase(requireContext())))
        setHasOptionsMenu(true)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNearPubListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelNearPubList = viewModel

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        )
        viewModel.deleteNearPubs()



        val myTask = myFusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)
        //val myTask = myFusedLocationClient.lastLocation
        myTask.addOnSuccessListener{
            it?.let {
                viewModel.insertNearPubs(myTask.result.latitude,myTask.result.longitude)
            }

        }
        viewModel.status.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()

        }


        recyclerView = binding.recyclerViewNearPub
        viewModel.allNearPubs.observe(viewLifecycleOwner){
            if (viewModel.myPub.value==null && !viewModel.allNearPubs.value.isNullOrEmpty()){
                viewModel.myPub.value = viewModel.allNearPubs.value?.get(0)
            }
            recyclerView.adapter = NearPubAdapter(it, viewModel)
        }

        viewModel.check.observe(viewLifecycleOwner){
            viewModel.myPub.value = it
            println(viewModel.myPub.value?.nearId)
            recyclerView.adapter =
                viewModel.allNearPubs.value?.let { it1 -> NearPubAdapter(it1, viewModel) }
        }

        binding.recyclerViewNearPub.layoutManager = LinearLayoutManager(this.context)

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