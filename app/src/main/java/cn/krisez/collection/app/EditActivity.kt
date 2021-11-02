package cn.krisez.collection.app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        binding.etRec.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val content = s.toString()
                if (content.isEmpty()) {
                    return
                }
                val index = when {
                    content.contains("magnet", true) -> {
                        content.indexOf("magnet", ignoreCase = true)
                    }
                    content.contains("http") -> {
                        content.indexOf("http", ignoreCase = true)
                    }
                    else -> -1
                }
                if (index == -1) {
                    binding.etName.setText(content)
                    return
                }
                val space = content.indexOf(" ", index)
                if (space == -1) {
                    binding.etLink.setText(content.substring(index))
                    if (index > 0) {
                        binding.etName.setText(content.substring(0, index))
                    }
                } else {
                    binding.etLink.setText(content.substring(index, space))
                    if (index > 0) {
                        binding.etName.setText(content.substring(0, index))
                    } else {
                        binding.etName.setText(content.substring(space+1))
                    }
                }
            }
        })
        binding.save.setOnClickListener {
            if (binding.etLink.text.isEmpty()) {
                toast("链接不能为空")
                return@setOnClickListener
            }
            model.insert(
                this,
                CollectionItem(
                    id, binding.etName.text.toString(),
                    binding.etLink.text.toString(),
                    binding.etStorageSpace.text.toString(),
                    TimeUtil.now
                )
            )
        }
        model.close.observe(this) {
            if (it) {
                finish()
            }
        }
    }
}