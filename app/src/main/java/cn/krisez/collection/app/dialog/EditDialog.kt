package cn.krisez.collection.app.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import cn.krisez.collection.app.R
import cn.krisez.collection.app.databinding.DialogEditBinding

class EditDialog(context: Context, val query: (search: String) -> Unit) : Dialog(context,R.style.dialog) {
    private lateinit var binding: DialogEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle()
        binding = DialogEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvSearch.setOnClickListener {
            dismiss()
            query(binding.editQuery.text.toString())
        }
        binding.editQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query(binding.editQuery.text.toString())
                dismiss()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setStyle() {
        val window = window
        //设置dialog在屏幕底部
        window!!.setGravity(Gravity.CENTER)
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.decorView.setPadding(0, 0, 0, 0)
        //获得window窗口的属性
        val lp = window.attributes
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //将设置好的属性set回去
        window.attributes = lp
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}