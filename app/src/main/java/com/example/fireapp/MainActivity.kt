package com.example.fireapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fireapp.databinding.ActivityMainBinding
import com.example.fireapp.fragments.FirestoreFragment
import com.example.fireapp.fragments.RealtimeFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    val database = Firebase.database
    val myRef = database.getReference("message")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null){
            changeFragment(RealtimeFragment(), binding.root.fragmentContainerView.id)
        }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuBtnChangeFragment){
            when (supportFragmentManager.fragments[0]){
                is FirestoreFragment -> {
                    supportFragmentManager.popBackStack()
                    changeFragment(RealtimeFragment(), binding.root.fragmentContainerView.id)
                    myRef.setValue("Hello, World!")
                }
                is RealtimeFragment -> {
                    supportFragmentManager.popBackStack()
                    changeFragment(FirestoreFragment(), binding.root.fragmentContainerView.id)
                    myRef.setValue("Hello, Paris!")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Fragment, fragmentContainerId: Int){
        supportFragmentManager.beginTransaction().apply{
            replace(fragmentContainerId, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        }
        super.onBackPressed()
    }
}