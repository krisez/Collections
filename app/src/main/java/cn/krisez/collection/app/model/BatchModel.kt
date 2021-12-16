package cn.krisez.collection.app.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.krisez.collection.app.db.DB
import cn.krisez.collection.app.entity.BatchSplit
import cn.krisez.collection.app.entity.CollectionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BatchModel : ViewModel() {
    val close by lazy {
        MutableLiveData<Boolean>()
    }
    val usage = MutableLiveData<MutableList<CollectionItem>>()

    fun insert(vararg items: CollectionItem) {
        viewModelScope.launch {
            val batch = withContext(Dispatchers.IO) {
                val use = arrayListOf<CollectionItem>()
                val nouse = arrayListOf<CollectionItem>()
                items.forEach {
                    val q = DB.getDB?.dao()?.getItem(it.link)
                    if (q == null) {
                        nouse.add(it)
                    } else {
                        use.add(it)
                    }
                }
                BatchSplit(use, nouse)
            }
            if (!batch.nouse.isNullOrEmpty()) {
                insertItems(*batch.nouse.toTypedArray())
            }
            usage.value = batch.use?.toMutableList()
        }
    }

    private fun insertItems(vararg item: CollectionItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DB.getDB?.dao()?.insertItem(*item)
            }
        }
    }
}