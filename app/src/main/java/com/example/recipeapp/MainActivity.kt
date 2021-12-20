package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private lateinit var btAdd: Button
    private lateinit var recipes: List<Recipe>

    // We declare apiInterface here to get access in functions
    // We use lazy initialization to make sure it only get initialized the first time we use it
    private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }

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

        // API call without coroutines (we use enqueue to start a new thread, which is less efficient than coroutines)
//        noCoroutines()

        // Here we do the same thing with Coroutines
        // We will use the await function to get a list of recipes
//        coroutinesGetList()

        // We can also use awaitResponse to get access to the response body to handle request errors
//        coroutinesGetResponseBody()

        // Now let's have a look at a cleaner setup that uses suspend functions
        coroutinesAndSuspend()
    }

    private fun noCoroutines(){
        apiInterface?.getAll()?.enqueue(object: Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                recipes = response.body()!!
                rvAdapter.update(recipes)
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.d("MAIN", "Unable to get data")
            }
        })
    }

    private fun coroutinesGetList(){
        CoroutineScope(IO).launch {
            val allRecipes = apiInterface?.getAll()?.await()
            try{
                recipes = allRecipes!!
            }catch(e: Exception){
                Log.d("MAIN", "Exception: $e")
            }
            withContext(Main){
                rvAdapter.update(recipes)
            }
        }
    }

    private fun coroutinesGetResponseBody(){
        CoroutineScope(IO).launch {
            try{
                val response = apiInterface!!.getAll().awaitResponse()
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

    private fun coroutinesAndSuspend(){
        CoroutineScope(IO).launch {
            try{
                // Now we no longer need the awaitResponse function
                val response = apiInterface!!.getAllResponse()
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