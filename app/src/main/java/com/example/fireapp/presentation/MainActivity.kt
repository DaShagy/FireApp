package com.example.fireapp.presentation

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fireapp.R
import com.example.fireapp.databinding.ActivityMainBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.fragments.FirestoreFragment
import com.example.fireapp.presentation.fragments.RealtimeFragment
import com.example.fireapp.presentation.viewmodels.FirestoreViewModel
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import com.example.fireapp.util.ResultWrapper
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    private val firestoreViewModel by viewModel<FirestoreViewModel>()
    private val realtimeViewModel by viewModel<RealtimeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null){
            changeFragment(RealtimeFragment(), binding.root.fragmentContainerView.id)
        }

        val pokemonNameEditText = binding.editTextPokemonName
        val pokemonIdEditText = binding.editTextPokemonId
        val pokemonOrderEditText = binding.editTextPokemonOrder
        val pokemonHeightEditText = binding.editTextPokemonHeight
        val pokemonWeightEditText = binding.editTextPokemonWeight

        val addPokemonBtn = binding.btnAddPokemon
        addPokemonBtn.setOnClickListener {
            val pokemon = Pokemon(
                id = pokemonIdEditText.text.toString().toIntOrNull(),
                name = pokemonNameEditText.text.toString(),
                order = pokemonOrderEditText.text.toString().toIntOrNull(),
                height = pokemonHeightEditText.text.toString().toIntOrNull(),
                weight = pokemonWeightEditText.text.toString().toIntOrNull()
            )
            addPokemon(pokemon).also {
                pokemonNameEditText.text.clear()
                pokemonIdEditText.text.clear()
                pokemonOrderEditText.text.clear()
                pokemonHeightEditText.text.clear()
                pokemonWeightEditText.text.clear()
            }
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
                }
                is RealtimeFragment -> {
                    supportFragmentManager.popBackStack()
                    changeFragment(FirestoreFragment(), binding.root.fragmentContainerView.id)
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

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
        }
        super.onBackPressed()
    }

    private fun addPokemon(pokemon: Pokemon){
        when (supportFragmentManager.fragments[0]){
            is FirestoreFragment -> {
                firestoreViewModel.insertPokemon(pokemon)
            }
            is RealtimeFragment -> {
                realtimeViewModel.insertPokemon(pokemon)
            }
        }
    }
}