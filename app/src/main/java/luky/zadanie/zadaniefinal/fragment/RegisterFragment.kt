package luky.zadanie.zadaniefinal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import luky.zadanie.zadaniefinal.PreferenceData
import luky.zadanie.zadaniefinal.Repository
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.databinding.FragmentRegisterBinding
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.viewmodel.AuthentificationViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
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
        _binding= FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModelRegister = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.registerButton.setOnClickListener{
            if (!checkingInput()){
                Snackbar.make(
                    binding.root.rootView,
                    "Please check your input field. Name or password is incorrect.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            else{
                viewModel.register(
                    binding.registerNameInput.text.toString(),
                    binding.registerPasswordInput.text.toString()
                )

            }
        }


        binding.loginTextButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }


        viewModel.user.observe(viewLifecycleOwner) {
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(), it)
                val action = RegisterFragmentDirections.actionRegisterFragmentToPubListFragment()
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

    private fun checkingInput():Boolean{
        return if (binding.registerNameInput.text.isNullOrBlank()){
            false
        } else if (binding.registerPasswordInput.text.isNullOrBlank()){
            false
        } else if (binding.registerRepeatPasswordInput.text.isNullOrBlank()){
            false
        } else passwordCheck()
    }

    private fun passwordCheck(): Boolean{

        return (binding.registerPasswordInput.text.toString() == binding.registerRepeatPasswordInput.text.toString())
    }
}