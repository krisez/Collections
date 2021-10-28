package cn.krisez.collection.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.krisez.collection.app.databinding.ActivityEditBinding
import cn.krisez.collection.app.entity.CollectionItem
import cn.krisez.collection.app.model.RoomModel
import cn.krisez.collection.app.utils.TimeUtil
import cn.krisez.collection.app.utils.viewModels

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private val model: RoomModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "新增/编辑"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.save.setOnClickListener {
            model.insert(
                CollectionItem(
                    null, binding.etName.text.toString(),
                    binding.etLink.text.toString(),
                    binding.etStorageSpace.text.toString(),
                    TimeUtil.now
                )
            )
        }

    }
}