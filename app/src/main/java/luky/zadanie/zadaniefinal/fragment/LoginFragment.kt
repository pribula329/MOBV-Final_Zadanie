package luky.zadanie.zadaniefinal.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.PreferenceData
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentLoginBinding
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.AuthentificationViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthentificationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = AuthentificationViewModel(Repository.getInstance(ApiService.create(requireContext()),
            PubRoomDatabase.getDatabase(requireContext())))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelLogin= viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = PreferenceData.getInstance().getUserItem(requireContext())
        if ((user?.uid ?: "").isNotBlank()) {
            val action = LoginFragmentDirections.actionLoginFragmentToPubListFragment()
            view.findNavController().navigate(action)
            return
        }

        binding.loginButton.setOnClickListener{
            if (!checkingInput()){
                Snackbar.make(
                    binding.root.rootView,
                    "Please check your input field. Name or password is incorrect",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            else{
                viewModel.login(
                    binding.loginNameInput.text.toString(),
                    binding.loginPasswordInput.text.toString()
                )

            }
        }

        binding.registerTextButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            view.findNavController().navigate(action)
        }


        viewModel.user.observe(viewLifecycleOwner) {
            println(it)
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(), it)
                val action = LoginFragmentDirections.actionLoginFragmentToPubListFragment()
                view.findNavController().navigate(action)
            }
        }

        viewModel.status.observe(viewLifecycleOwner){
            Snackbar.make(
                binding.root.rootView,
                it.toString(),
                Snackbar.LENGTH_SHORT
            ).show()
        }


    }

    private fun checkingInput(): Boolean{
        return if (binding.loginNameInput.text.isNullOrBlank()){
            false
        } else !binding.loginPasswordInput.text.isNullOrBlank()

    }


}