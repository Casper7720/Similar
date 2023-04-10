package com.example.similarphotos.presentation

import com.example.similarphotos.presentation.screens.loadScreen.LoadFragment
import com.example.similarphotos.presentation.screens.photos.PhotosFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun loadFragment(loadStatus: LoadStatus) = FragmentScreen{LoadFragment.getNewInstance(loadStatus)}
    fun photosFragment() = FragmentScreen{PhotosFragment()}
}