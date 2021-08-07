package net.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_fragment_history.*
import net.HistoryEntity
import net.adapter.HistoryAdapter
import net.basicmodel.R
import net.utils.MMKVUtils
import net.utils.OnItemChildClickListener
import net.utils.ShareUtils

/**
 * Copyright (C) 2021,2021/8/3, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class HistoryFragment : Fragment(), OnItemChildClickListener {

    var data: ArrayList<HistoryEntity> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = MMKVUtils.getAllDatas("time")
        if (data.size == 0) {
            emptyView.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            val adapter = HistoryAdapter(data, activity)
            val layoutManager = LinearLayoutManager(activity)
            recycler.layoutManager = layoutManager
            recycler.adapter = adapter
            adapter.setListener(this)
        }

    }

    override fun onItemChildClick(view: View?, position: Int, flag: String?) {
        val entity: HistoryEntity = data[position]
        ShareUtils.share(activity, entity.content)
    }
}