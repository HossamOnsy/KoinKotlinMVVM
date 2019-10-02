package com.example.koinkotlinmvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.koinkotlinmvvm.MainViewModel
import com.example.koinkotlinmvvm.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    // Instantiate viewModel with Koin
    private val mainViewModel: MainViewModel by inject()
    private lateinit var catAdapter: CatAdapter

    // first function that is entered in order to create the MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing recyclerview with required parameters
        initiateRecyclerView()
        // Initiate the observers on viewModel fields and then starts the API request
        initViewModel()
    }

    private fun initiateRecyclerView() {
        // initializing catAdapter with empty list
        catAdapter = CatAdapter(emptyList())
        // apply allows you to alter variables inside the object and assign them
        catsRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3)
        catsRecyclerView.adapter = catAdapter

    }

    fun initViewModel() {

        // Observe exceptionMessageReceived value and display the error message as a Toast
        mainViewModel.exceptionMessageReceived.observe(this, Observer { exceptionMessageReceived ->
            Toast.makeText(this, exceptionMessageReceived, Toast.LENGTH_SHORT).show()
        })
        // Observe catListRetrievedSuccessfully and update our adapter if we get new list from API
        mainViewModel.catListRetrievedSuccessfully.observe(this, Observer { newCatsList ->
            catAdapter.updateData(newCatsList!!)
        })

        // telling our viewModel to start fetching cats from our api
        mainViewModel.loadCats()
    }

}
