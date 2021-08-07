package net.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.layout_fragment_create.*
import net.HistoryEntity
import net.basicmodel.R
import net.utils.*
import java.util.*

/**
 * Copyright (C) 2021,2021/8/3, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class CreateFragment : Fragment() {

    val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
                    val entity = HistoryEntity()
                    entity.type = "create"
                    entity.content = createEt.text.toString()
                    val time = FormatUtils.formatDate(Date())
                    entity.time = time
                    MMKVUtils.saveKeys("time",time)
                    MMKV.defaultMMKV()?.encode(time,entity)
                }
                -1 -> {
                    Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        createBtn.setOnClickListener {
            if (TextUtils.isEmpty(createEt.text.toString())) {
                Toast.makeText(activity, "please input something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val width = ScreenUtils.getScreenSize(activity)[1]
            val height = ScreenUtils.getScreenSize(activity)[0]
            val bitmap =
                EncodingUtils.createQRCode(createEt.text.toString(), height / 3, height / 3)
                    ?: return@setOnClickListener
            createImg.setImageBitmap(bitmap)
            Thread(Runnable {
                val result: Boolean = ImgUtils.saveImageToGallery(activity, bitmap)
                if (result) {
                    val msg = Message()
                    msg.what = 1
                    handler.sendMessage(msg)
                } else {
                    val msg = Message()
                    msg.what = -1
                    handler.sendMessage(msg)
                }

            }).start()

        }
    }
}