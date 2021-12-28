package com.example.fireapp.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.fireapp.R
import com.example.fireapp.databinding.ActivityMainBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.fragments.FirestoreFragment
import com.example.fireapp.presentation.fragments.RealtimeFragment
import com.example.fireapp.presentation.viewmodels.FirestoreViewModel
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import com.example.fireapp.util.Const
import com.example.fireapp.util.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!

    private val firestoreViewModel by viewModels<FirestoreViewModel>()
    private val realtimeViewModel by viewModels<RealtimeViewModel>()
    private var cardPokemon : Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        createNotificationChannel()

        firestoreViewModel.pokemonList.observe(this){
            showNotification("Database", "Database has been updated", NOTIFICATION_ID)
        }

        realtimeViewModel.pokemonList.observe(this){
            showNotification("Database", "Database has been updated", NOTIFICATION_ID)
        }


        if (savedInstanceState == null){
            changeFragment(RealtimeFragment(), binding.root.fragmentContainerView.id)
        }

        binding.btnAddPokemon.setOnClickListener {
            onAddPokemonButtonClick()
        }

        binding.root.cardBtnEditPokemon.setOnClickListener {
            cardPokemon?.let { pokemon -> onCardEditButtonClick(pokemon) }
        }

        binding.root.cardBtnDeletePokemon.setOnClickListener {
            cardPokemon?.let { pokemon -> onCardDeleteButtonClick(pokemon) }
            clearEditTextFields()
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
        clearEditTextFields()
        updateCard(null)
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

    fun updateCard(pokemon: Pokemon?){
        binding.root.cardPokemonName.text = "Name: ${pokemon?.name ?: ""}"
        binding.root.cardPokemonOrder.text = "Order #${pokemon?.order ?: "##"}"
        binding.root.cardPokemonHeight.text = "${pokemon?.height ?: ""}cm."
        binding.root.cardPokemonWeight.text = "${pokemon?.weight ?: ""}kg."
        cardPokemon = pokemon
    }

    private fun clearEditTextFields(){
        binding.root.editTextPokemonName.text.clear()
        binding.root.editTextPokemonId.text.clear()
        binding.root.editTextPokemonOrder.text.clear()
        binding.root.editTextPokemonHeight.text.clear()
        binding.root.editTextPokemonWeight.text.clear()
    }

    private fun onAddPokemonButtonClick(){
        if (
            binding.editTextPokemonId.text.toString().isNotBlank()
            and binding.editTextPokemonName.text.toString().isNotBlank()
            and binding.editTextPokemonOrder.text.toString().isNotBlank()
            and binding.editTextPokemonHeight.text.toString().isNotBlank()
            and binding.editTextPokemonWeight.text.toString().isNotBlank()
        ) {
            val pokemon = Pokemon(
                id = binding.editTextPokemonId.text.toString().toIntOrNull(),
                name = binding.editTextPokemonName.text.toString(),
                order = binding.editTextPokemonOrder.text.toString().toIntOrNull(),
                height = binding.editTextPokemonHeight.text.toString().toIntOrNull(),
                weight = binding.editTextPokemonWeight.text.toString().toIntOrNull()
            )
            addPokemon(pokemon).also {
                clearEditTextFields()

                if (binding.root.btnAddPokemon.text == Const.EDIT_POKEMON) {
                    binding.root.btnAddPokemon.text = Const.ADD_POKEMON
                    binding.editTextPokemonId.isEnabled = true
                }

                updateCard(Pokemon())
            }
        }
    }

    private fun onCardEditButtonClick(pokemon: Pokemon){

        binding.editTextPokemonName.setText(pokemon.name)
        binding.editTextPokemonId.apply {
            setText(pokemon.id.toString())
            isEnabled = false
        }
        binding.editTextPokemonOrder.setText(pokemon.order.toString())
        binding.editTextPokemonHeight.setText(pokemon.height.toString())
        binding.editTextPokemonWeight.setText(pokemon.weight.toString())

        binding.btnAddPokemon.text = Const.EDIT_POKEMON
    }

    private fun onCardDeleteButtonClick(pokemon: Pokemon){
        when (supportFragmentManager.fragments[0]){
            is FirestoreFragment -> {
                firestoreViewModel.deletePokemon(pokemon)
            }
            is RealtimeFragment -> {
                realtimeViewModel.deletePokemon(pokemon)
            }
        }
        updateCard(Pokemon())
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.RED
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, text: String, notificationId: Int){
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "channelId"
        const val NOTIFICATION_CHANNEL_NAME = "channelName"
        const val NOTIFICATION_ID = 0
    }
}