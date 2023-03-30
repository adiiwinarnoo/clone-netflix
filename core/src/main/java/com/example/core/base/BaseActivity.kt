package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.core.R
import com.example.core.exception.ApiErrorException
import com.example.core.exception.NoInternetException
import org.koin.android.ext.android.get
import java.lang.Error
import java.lang.Exception

abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    val bindingFactory : (LayoutInflater) -> B,
) : AppCompatActivity() {

    protected lateinit var binding : B
    protected abstract val viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    abstract fun initView()


    open fun observeData(){

    }

    open fun showError(isError: Boolean, exception: Exception){
        if (isError){
            Toast.makeText(this,getErrorMessage(exception), Toast.LENGTH_SHORT).show()
        }

    }

    private fun getErrorMessage(exception: Exception) : String {
        return when(exception){
           is NoInternetException -> getString(com.example.styling.R.string.message_error_no_internet)
           is ApiErrorException -> exception.message.orEmpty()
           else -> getString((com.example.styling.R.string.message_error_unknown))
        }
    }

    fun enableHomeAsBack(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}