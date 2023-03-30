package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.core.R
import com.example.core.exception.ApiErrorException
import com.example.core.exception.NoInternetException
import org.koin.android.ext.android.get
import java.lang.Error
import java.lang.Exception

abstract class BaseFragment<B : ViewBinding, VM : ViewModel>(
    val bindingFactory : (LayoutInflater, ViewGroup?, Boolean) -> B,
) : Fragment() {

    protected lateinit var binding : B
    protected abstract val viewModel : VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }


    abstract fun initView()


    open fun observeData(){

    }

    open fun showError(isError: Boolean, exception: Exception){
        if (isError){
            Toast.makeText(requireContext(),getErrorMessage(exception), Toast.LENGTH_SHORT).show()
        }

    }

    private fun getErrorMessage(exception: Exception) : String {
        return when(exception){
           is NoInternetException -> getString(com.example.styling.R.string.message_error_no_internet)
           is ApiErrorException -> exception.message.orEmpty()
           else -> getString((com.example.styling.R.string.message_error_unknown))
        }
    }
}