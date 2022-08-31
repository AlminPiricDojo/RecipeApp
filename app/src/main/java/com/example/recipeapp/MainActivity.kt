package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var btAdd: Button
    private lateinit var recipes: List<Recipe>

    @Inject
    lateinit var apiInterface: APIInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipes = listOf()

        rvMain = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(recipes)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        btAdd = findViewById(R.id.btAdd)
        btAdd.setOnClickListener {
            val intent = Intent(this, AddRecipe::class.java)
            startActivity(intent)
        }

        coroutinesAndSuspend()
    }

    private fun coroutinesAndSuspend(){
        CoroutineScope(IO).launch {
            try{
                val response = apiInterface.getAllResponse()
                if(response.isSuccessful){
                    recipes = response.body()!!
                }else{
                    Log.d("MAIN", "Unable to get data.")
                }
            }catch(e: Exception){
                Log.d("MAIN", "Exception: $e")
            }
            withContext(Main){
                rvAdapter.update(recipes)
            }
        }
    }
}