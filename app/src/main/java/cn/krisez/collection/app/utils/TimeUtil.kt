package cn.krisez.collection.app.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    /**
     * 格式化当前时间
     *
     * @return
     */
    val now: String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return sdf.format(Date())
        }
}