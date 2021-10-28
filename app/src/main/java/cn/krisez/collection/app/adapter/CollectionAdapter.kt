package cn.krisez.collection.app.adapter

import cn.krisez.collection.app.R
import cn.krisez.collection.app.entity.CollectionItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class CollectionAdapter :
    BaseQuickAdapter<CollectionItem, BaseViewHolder>(R.layout.item_collection) {
    override fun convert(holder: BaseViewHolder, item: CollectionItem) {
        holder.setText(R.id.item_tv_name, item.name)
        holder.setText(R.id.item_tv_space, item.size)
        holder.setText(R.id.item_tv_link, item.link)
        holder.setText(R.id.item_tv_time, context.getString(R.string.modify_time, item.updateTime))
        addChildClickViewIds(R.id.item_iv_copy, R.id.item_iv_delete)
    }
}