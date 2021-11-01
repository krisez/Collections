package cn.krisez.collection.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.krisez.collection.app.databinding.ActivityEditBinding
import cn.krisez.collection.app.entity.CollectionItem
import cn.krisez.collection.app.model.RoomModel
import cn.krisez.collection.app.utils.TimeUtil
import cn.krisez.collection.app.utils.toast
import cn.krisez.collection.app.utils.viewModels

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private val model: RoomModel by viewModels()
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getParcelableExtra<CollectionItem>("entity")?.let {
            id = it.id
            binding.etName.setText(it.name)
            binding.etLink.setText(it.link)
            binding.etStorageSpace.setText(it.size)
            binding.tvTime.text = it.updateTime
        }
        binding.toolbar.title = "新增/编辑"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.save.setOnClickListener {
            if (binding.etLink.text.isEmpty()) {
                toast("链接不能为空")
                return@setOnClickListener
            }
            model.insert(
                CollectionItem(
                    id, binding.etName.text.toString(),
                    binding.etLink.text.toString(),
                    binding.etStorageSpace.text.toString(),
                    TimeUtil.now
                )
            )
            finish()
        }
    }
}