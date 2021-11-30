package com.example.fireapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fireapp.R
import com.example.fireapp.databinding.ActivityMainBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.fragments.FirestoreFragment
import com.example.fireapp.presentation.fragments.RealtimeFragment
import com.example.fireapp.presentation.viewmodels.MainViewModel
import com.example.fireapp.util.ResultWrapper
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null){
            changeFragment(RealtimeFragment(), binding.root.fragmentContainerView.id)
        }

        mainViewModel.pokemonList.observe(this, ::updateUI)

        setContentView(binding.root)
    }

    private fun updateUI(resultWrapper: ResultWrapper<List<Pokemon>>) {
        when (resultWrapper){
            is ResultWrapper.Fail -> showMessage(resultWrapper.exception.toString())
            is ResultWrapper.Success -> showMessage(resultWrapper.data.toString())
        }
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
                }
                is RealtimeFragment -> {
                    supportFragmentManager.popBackStack()
                    changeFragment(FirestoreFragment(), binding.root.fragmentContainerView.id)
                }
            }
            mainViewModel.getAllPokemon()
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

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        }
        super.onBackPressed()
    }
}