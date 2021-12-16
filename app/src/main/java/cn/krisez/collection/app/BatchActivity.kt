package cn.krisez.collection.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.krisez.collection.app.databinding.ActivityBatchBinding
import cn.krisez.collection.app.entity.CollectionItem
import cn.krisez.collection.app.model.BatchModel
import cn.krisez.collection.app.utils.TimeUtil
import cn.krisez.collection.app.utils.toast
import cn.krisez.collection.app.utils.viewModels
import java.util.*

class BatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBatchBinding
    private val model: BatchModel by viewModels()
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.save.setOnClickListener {
            if (binding.etBatch.text.trim().isEmpty()) {
                toast("内容为空~")
                return@setOnClickListener
            }
            val list = binding.etBatch.text.toString().split(binding.etChars.text.toString(), ignoreCase = true).filter {
                it.isNotEmpty()
            }
            if (list.isNullOrEmpty()) {
                return@setOnClickListener
            }
            val collectionItem = list.map {
                CollectionItem(
                    null,
                    "",
                    "${binding.etChars.text}$it".trim(),
                    "",
                    TimeUtil.now
                )
            }
            model.insert(*collectionItem.toTypedArray())
        }
        model.close.observe(this)
        {
            if (it) {
                finish()
            }
        }
        model.usage.observe(this) {
            val edit = binding.etBatch
            edit.setText("")
            it.forEach { item ->
                edit.append(item.link)
                edit.append("\n")
            }
            if (it.isNotEmpty()) {
                toast("内容含有未保存")
            }
        }
    }
}