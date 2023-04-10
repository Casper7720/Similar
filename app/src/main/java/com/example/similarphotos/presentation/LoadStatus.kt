package com.example.similarphotos.presentation

enum class LoadStatus {
    DOWNLOAD, DELETE;

    fun getType(loadStatus: LoadStatus): Int{
        return when(loadStatus){
            DOWNLOAD -> 1
            DELETE ->2
        }
    }

    fun getStatus(type: Int): LoadStatus{
        return when(type){
            1 -> DOWNLOAD
            2 -> DELETE
            else -> {DELETE}
        }
    }
}