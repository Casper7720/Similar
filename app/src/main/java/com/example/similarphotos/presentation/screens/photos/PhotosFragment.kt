package com.example.similarphotos.presentation.screens.photos

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.similarphotos.R
import com.example.similarphotos.databinding.FragmentPhotosBinding
import com.example.similarphotos.presentation.LoadStatus
import com.example.similarphotos.presentation.PhotoItem
import com.example.similarphotos.presentation.Screens
import com.example.similarphotos.presentation.base.BaseFragment
import com.example.similarphotos.presentation.screens.photos.adapter.RecyclersAdapter
import com.example.similarphotos.presentation.screens.photos.adapter.RecyclersVH
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotosFragment : BaseFragment<PhotosViewModel, FragmentPhotosBinding>(
    R.layout.fragment_photos
) {

    override val binding: FragmentPhotosBinding by viewBinding(FragmentPhotosBinding::bind)
    override val viewModel: PhotosViewModel by viewModels()

    private var listItems: List<PhotoItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRouter().navigateTo(Screens.loadFragment(LoadStatus.DOWNLOAD))
    }

    override fun initialize() {

        super.initialize()
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.deletePhotoButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                this.activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(),
                        MY_READ_PERMISSION_CODE
                    )
                }
            } else {
                deletePhoto()
            }
        }
    }

    override fun setupRequests() {
        super.setupRequests()
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    listOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),
                    MY_READ_PERMISSION_CODE
                )
            }
        } else {
            setImages()
        }
    }

    private fun setImages() {
        val images = loadImage()


        binding.recyclersRv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RecyclersAdapter()

        listItems = images.map { item ->
            PhotoItem(
                id = System.currentTimeMillis(),
                Uri = item.toString(),
                isCheck = false
            )
        }

        adapter.setData(listOf(listItems))
        adapter.setListener(object : RecyclersVH.RootListener {
            override fun update(list: List<PhotoItem>) {
                listItems = list
            }
        })
        binding.recyclersRv.adapter = adapter
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                setImages()
            }
        }

        if (requestCode == MY_WRITE_PERMISSION_CODE) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                deletePhoto()
            }
        }


    }

    fun loadImage(): List<Uri> {
        val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<Uri>()
        var absolutePathOfImage: String? = null

        cursor = requireContext().contentResolver.query(
            uri, null, null,
            null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            var id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))

            do {
                var pathId = cursor.getString(column_index_data)
                var uri = Uri.parse(pathId)
                listOfAllImages.add(uri)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        return listOfAllImages
    }

    private fun deletePhoto() {
        if (listItems.firstOrNull { item -> item.isCheck } != null) {
            getRouter().navigateTo(Screens.loadFragment(LoadStatus.DELETE))


            listItems.forEach {
                if (it.isCheck) {
                    val contentResolver: ContentResolver = requireContext().contentResolver
                    contentResolver.delete(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.ImageColumns.DATA + "=?", arrayOf(it.Uri)
                    )
                }
            }
        }

    }


    companion object {
        val MY_READ_PERMISSION_CODE = 101
        val MY_WRITE_PERMISSION_CODE = 102
    }

}