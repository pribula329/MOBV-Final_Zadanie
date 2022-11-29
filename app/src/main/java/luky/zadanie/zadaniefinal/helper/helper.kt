package luky.zadanie.zadaniefinal.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import luky.zadanie.zadaniefinal.PreferenceData
import luky.zadanie.zadaniefinal.R
import luky.zadanie.zadaniefinal.fragment.LoginFragmentDirections
import luky.zadanie.zadaniefinal.fragment.PubListFragmentDirections


fun getIconPub(typePub: String): Int {
    if (typePub=="cafe"){
        return R.drawable.baseline_local_cafe_24
    }
    else if (typePub=="fast_food"){
        return R.drawable.baseline_fastfood_24
    }
    else if(typePub=="restaurant"){
        return R.drawable.baseline_restaurant_24
    }
    else
        return R.drawable.baseline_store_24
    }


fun logOut(view: View, context: Context?){
    PreferenceData.getInstance().clearData(context)
    Navigation.findNavController(view).navigate(R.id.loginFragment)
}



