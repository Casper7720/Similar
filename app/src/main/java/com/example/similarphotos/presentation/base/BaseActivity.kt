package com.example.similarphotos.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.similarphotos.presentation.App
import com.github.terrakok.cicerone.Router

abstract class BaseActivity<ViewModel : BaseViewModel, Binding : ViewBinding>(
    @LayoutRes layoutId: Int
) : AppCompatActivity(layoutId) {

    protected abstract val viewModel: ViewModel
    protected abstract val binding: Binding

    fun getRouter(): Router = App.INSTANCE.router

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        setupListeners()
        setupRequests()
        setupSubscribers()
    }

    protected open fun initialize() {
    }

    protected open fun setupListeners() {
    }

    protected open fun setupRequests() {
    }

    protected open fun setupSubscribers() {
    }
}