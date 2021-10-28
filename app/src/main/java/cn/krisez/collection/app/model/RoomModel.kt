package cn.krisez.collection.app.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.krisez.collection.app.db.DB
import cn.krisez.collection.app.entity.CollectionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomModel : ViewModel() {
    val data = MutableLiveData<MutableList<CollectionItem>>()

    fun load() {
        viewModelScope.launch(Dispatchers.Main) {
            data.value = withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.queryAll()
            }?.toMutableList()
        }
    }

    fun insert(item: CollectionItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (item.id != null) {
                    DB.getDB?.dao()?.updateItems(item)
                } else {
                    DB.getDB?.dao()?.insertItem(item)
                }
            }
        }
    }
}