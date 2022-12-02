package luky.zadanie.zadaniefinal.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentPubDetailBinding
import luky.zadanie.zadaniefinal.helper.getIconPub
import luky.zadanie.zadaniefinal.helper.logOut
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.PubDetailViewModel

@Suppress("DEPRECATION")
class PubDetailFragment : Fragment() {
    private var _binding: FragmentPubDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PubDetailViewModel
    private val args: PubDetailFragmentArgs by navArgs()

    private var isVisibleNav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PubDetailViewModel(Repository.getInstance(ApiService.create(requireContext()),
            PubRoomDatabase.getDatabase(requireContext())))
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPubDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelPubDetail= viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailPub(args.pubIdDetail)

        textInformation()
        startAnimation()

        viewModel.status.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()

        }

        viewModel.pubDetail.observe(viewLifecycleOwner){
            var count = 0
            if (viewModel.pubDetail.value?.website?.isNotBlank() == true){
               val button = view.findViewById<Button>(R.id.websiteView)
               button.visibility = View.VISIBLE
                count=1
            }
            if (viewModel.pubDetail.value?.email?.isNotBlank() == true){
                val button = view.findViewById<Button>(R.id.emailView)
                button.visibility = View.VISIBLE
                count = 1
            }
            if (viewModel.pubDetail.value?.phone?.isNotBlank() == true){
                val button = view.findViewById<Button>(R.id.phoneView)
                button.visibility = View.VISIBLE
                count = 1
            }

            if (count==1){
                val anima = view.findViewById<LottieAnimationView>(R.id.animationView)
                anima.visibility = View.GONE
            }
        }


        binding.showButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:${args.latDetail},${args.lonDetail}?q=${args.latDetail},${args.lonDetail}(${args.pubNameDetail})")
            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")
            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent)
        }

        binding.websiteView.setOnClickListener {
            val webUri = Uri.parse(viewModel.pubDetail.value?.website)
            val websiteIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(websiteIntent)
        }

        binding.phoneView.setOnClickListener {
            val phoneUri = Uri.parse("tel:${viewModel.pubDetail.value?.phone}")
            val phoneIntent = Intent(Intent.ACTION_DIAL, phoneUri)
            startActivity(phoneIntent)
        }
        binding.emailView.setOnClickListener {
            val emailUri = Uri.parse("mailto:${viewModel.pubDetail.value?.email}")
            val emailIntent = Intent(Intent.ACTION_SENDTO, emailUri)
            startActivity(emailIntent)
        }


        binding.floatingActionButton.setOnClickListener {
            if (!isVisibleNav){
                binding.floatingActionButtonPubs.visibility = View.VISIBLE
                binding.floatingActionButtonAddDeleteFriends.visibility = View.VISIBLE
                binding.floatingActionButtonNearPubs.visibility = View.VISIBLE
                binding.floatingActionButtonFriend.visibility = View.VISIBLE
                isVisibleNav = !isVisibleNav
            }
            else{
                binding.floatingActionButtonPubs.visibility = View.GONE
                binding.floatingActionButtonAddDeleteFriends.visibility = View.GONE
                binding.floatingActionButtonNearPubs.visibility = View.GONE
                binding.floatingActionButtonFriend.visibility = View.GONE
                isVisibleNav = !isVisibleNav
            }
        }

        binding.floatingActionButtonPubs.setOnClickListener{
            val action = PubDetailFragmentDirections.actionPubDetailFragmentToPubListFragment()
            view.findNavController().navigate(action)
        }

        binding.floatingActionButtonAddDeleteFriends.setOnClickListener {
            val action = PubDetailFragmentDirections.actionPubDetailFragmentToAddDeleteFriendFragment()
            view.findNavController().navigate(action)
        }

        binding.floatingActionButtonFriend.setOnClickListener {
            val action = PubDetailFragmentDirections.actionPubDetailFragmentToFriendFragment()
            view.findNavController().navigate(action)
        }
        binding.floatingActionButtonNearPubs.setOnClickListener {
            val action = PubDetailFragmentDirections.actionPubDetailFragmentToNearPubListFragment()
            view.findNavController().navigate(action)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun textInformation(){
        binding.apply {
            pubNameView.text = args.pubNameDetail
            pubTypeView.setImageResource(getIconPub(args.pubTypeDetail))
            pubTypeTextView.text = args.pubTypeDetail
            pubCount.text = "People in pub: ${args.userCountDetail}"

        }

    }

    private fun startAnimation(){
        val screen: View = binding.showScreen
        val animationCoctail : LottieAnimationView = binding.animationView
        animationCoctail.playAnimation()
        screen.setOnClickListener {
            stopAndPlayAnimation(animationCoctail)
        }
    }

    private fun stopAndPlayAnimation(animationCoctail: LottieAnimationView){

        if (animationCoctail.isAnimating){
            animationCoctail.pauseAnimation()
        }
        else{
            animationCoctail.resumeAnimation()
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