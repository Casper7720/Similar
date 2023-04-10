package com.example.similarphotos.presentation.activity

import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.similarphotos.R
import com.example.similarphotos.databinding.ActivityMainBinding
import com.example.similarphotos.presentation.App
import com.example.similarphotos.presentation.Screens
import com.example.similarphotos.presentation.base.BaseActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(
    R.layout.activity_main
) {

    override val viewModel: MainActivityViewModel by viewModels()
    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val navigator = AppNavigator(this, R.id.fragment_container)

    override fun initialize() {
        super.initialize()

        binding.toolbar.title = getString(R.string.similar_photos)

        getRouter().navigateTo(Screens.photosFragment())

    }

    override fun setupListeners() {
        super.setupListeners()
    }

    override fun setupRequests() {
        super.setupRequests()
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
    }

    override fun onResume() {
        super.onResume()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

}