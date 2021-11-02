package cn.krisez.collection.app.model

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.krisez.collection.app.App
import cn.krisez.collection.app.db.DB
import cn.krisez.collection.app.entity.CollectionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomModel : ViewModel() {
    val data = MutableLiveData<MutableList<CollectionItem>>()
    val close by lazy {
        MutableLiveData<Boolean>()
    }

    fun load() {
        viewModelScope.launch(Dispatchers.Main) {
            data.value = withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.queryAll()
            }?.toMutableList()
        }
    }

    fun insert(context: Context, item: CollectionItem) {
        viewModelScope.launch {
            val q = withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.getItem(item.link)
            }
            if (q == null || item.id != null) {
                insertItems(item)
            } else {
                AlertDialog.Builder(context).setMessage("已保存有该条链接，确定修改数据？")
                    .setPositiveButton("取消") { dialog, _ -> dialog.dismiss() }
                    .setNegativeButton("确定") { dialog, _ ->
                        dialog.dismiss()
                        insertItems(item)
                    }
                    .show()
            }
        }
    }

    private fun insertItems(item: CollectionItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.insertItem(item)
            }
            close.postValue(true)
        }
    }

    fun delete(item: CollectionItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.delete(item)
            }
        }
    }
}