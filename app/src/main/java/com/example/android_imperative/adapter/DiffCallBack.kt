package com.example.android_imperative.adapter


import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.android_imperative.model.TVShow

class DiffCallBack(oldList: List<TVShow>, newList: List<TVShow>) :
    DiffUtil.Callback() {
    private val mOldList: List<TVShow>
    private val mNewList: List<TVShow>
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldList[oldItemPosition]
        val newEmployee = mNewList[newItemPosition]
        return oldEmployee.name == newEmployee.name
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldList = oldList
        mNewList = newList
    }
}