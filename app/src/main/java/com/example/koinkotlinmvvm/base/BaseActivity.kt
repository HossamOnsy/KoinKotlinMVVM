package com.example.koinkotlinmvvm.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.koinkotlinmvvm.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    // Instantiate viewModel with Koin
    protected abstract fun getContentLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setupUI()
    }



    private fun setupUI() {
        llMainView.addView(layoutInflater.inflate(getContentLayout(), null))
    }
}
