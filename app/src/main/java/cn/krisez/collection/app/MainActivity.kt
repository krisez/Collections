package cn.krisez.collection.app

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cn.krisez.collection.app.adapter.CollectionAdapter
import cn.krisez.collection.app.databinding.ActivityMainBinding
import cn.krisez.collection.app.model.RoomModel
import cn.krisez.collection.app.utils.toast
import cn.krisez.collection.app.utils.viewModels
import com.alibaba.fastjson.JSON


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mAdapter = CollectionAdapter()
    private val model: RoomModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.item_iv_copy) {
                val copy = "${mAdapter.getItem(position).name}\n${mAdapter.getItem(position).link}"
                //获取剪贴板管理器：
                val cm: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText(copy, copy)
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData)
                toast("已复制")
            } else if (view.id == R.id.item_iv_delete) {
                AlertDialog.Builder(this).setMessage("确定删除？")
                    .setPositiveButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setNegativeButton("确定") { dialog, _ ->
                        model.delete(mAdapter.getItem(position))
                        mAdapter.removeAt(position)
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            startActivity(
                Intent(this, EditActivity::class.java).putExtra(
                    "entity",
                    mAdapter.getItem(position)
                )
            )
        }
        model.data.observe(this) { list ->
            Log.d("MainActivity", "onCreate: ${JSON.toJSON(list)}")
            mAdapter.setNewInstance(list)
        }
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun load() {
                model.load()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}