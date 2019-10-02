package com.example.koinkotlinmvvm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {


    // Instantiate viewModel with Koin
    private val mainViewModel: MainViewModel by inject()
    private lateinit var catAdapter: CatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        catAdapter = CatAdapter()
        catsRecyclerView.apply {
            // Displaying data in a Grid design
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = catAdapter
        }
        // Initiate the observers on viewModel fields and then starts the API request
        initViewModel()
    }


    fun initViewModel() {

        // Observe showLoading value and display or hide our activity's progressBar
        mainViewModel.showLoading.observe(this, Observer { showLoading ->
            mainProgressBar.visibility = if (showLoading!!) View.VISIBLE else View.GONE
        })
        // Observe showError value and display the error message as a Toast
        mainViewModel.showError.observe(this, Observer { showError ->
            Toast.makeText(this, showError, Toast.LENGTH_SHORT).show()
        })
        // Observe catsList and update our adapter if we get new one from API
        mainViewModel.catsList.observe(this, Observer { newCatsList ->
            catAdapter.updateData(newCatsList!!)
        })

        // The observers are set, we can now ask API to load a data list
        mainViewModel.loadCats()
    }

}
