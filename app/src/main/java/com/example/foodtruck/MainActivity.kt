package com.example.foodtruck

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodtruck.data.database.FoodTruckDatabase
import com.example.foodtruck.repository.FoodTruckRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
            val database = FoodTruckDatabase.getDatabase(this)
            val repository = FoodTruckRepository (database.dao())

            setContent {
                FoodTruckTheme {
                    modifier = Modificar.fillMaxSixe(
                    color = MaterialThema.colorScheme.background
                    ) {
                        FoodTruckNavigation( repository = repository)
                    }
                } 
        }
    }
}