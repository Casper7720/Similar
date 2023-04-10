package com.example.similarphotos.presentation.screens.loadScreen

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.similarphotos.R
import com.example.similarphotos.databinding.FragmentLoadBinding
import com.example.similarphotos.presentation.LoadStatus
import com.example.similarphotos.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.CountDownLatch

@AndroidEntryPoint
class LoadFragment : BaseFragment<LoadViewModel, FragmentLoadBinding>(
    R.layout.fragment_load
) {

    override val binding: FragmentLoadBinding by viewBinding(FragmentLoadBinding::bind)
    override val viewModel: LoadViewModel by viewModels()

    override fun initialize() {
        super.initialize()

        val loadStatus : LoadStatus = arguments?.getSerializable(EXTRA) as LoadStatus
        binding.loadTv.text = when(loadStatus){
            LoadStatus.DOWNLOAD -> "Поиск похожих фотографий"
            LoadStatus.DELETE -> "Удаление фотографий"
        }

        val timeStart = object : CountDownTimer(5000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progress.progress = binding.progress.progress+1
            }

            override fun onFinish() {
                getRouter().exit()
            }

        }
        timeStart.start()
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

    companion object{
        private const val EXTRA = "EXTRA"

        fun getNewInstance(loadStatus: LoadStatus): LoadFragment {
            return LoadFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA, loadStatus)
                }
            }
        }
    }
}