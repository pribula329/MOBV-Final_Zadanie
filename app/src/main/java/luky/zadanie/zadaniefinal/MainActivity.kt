package luky.zadanie.zadaniefinal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    companion object{
        private const val FINE_LOCATION_PER=100
        private const val COARSE_LOCATION_PER=101
        private const val BACKGROUND_LOCATION_PER=102
        private const val INTERNET_PER=103
    }


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* checkPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION,Manifest.permission.INTERNET),
            arrayOf(FINE_LOCATION_PER,COARSE_LOCATION_PER,BACKGROUND_LOCATION_PER, INTERNET_PER)
        )*/
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

    }







/*
    private fun checkPermission(permissions: Array<String>, requestCode: Array<Int>) {
        for ((i, per) in permissions.withIndex()){
            if (ContextCompat.checkSelfPermission(this@MainActivity, per) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(per), requestCode[i])
            }
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FINE_LOCATION_PER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == COARSE_LOCATION_PER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Coarse Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Coarse Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }else if (requestCode == BACKGROUND_LOCATION_PER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Background Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Background Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }else if (requestCode == INTERNET_PER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Internet Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Internet Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }*/
}