package net.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_fragment_create.*
import net.basicmodel.R
import net.utils.EncodingUtils
import net.utils.ScreenUtils

/**
 * Copyright (C) 2021,2021/8/3, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class CreateFragment : Fragment() {

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
        val p = createImg.layoutParams
        p.width = ScreenUtils.getScreenSize(activity)[0] * 3 / 4
        p.height = ScreenUtils.getScreenSize(activity)[1] / 2
        createImg.layoutParams = p
        createRoot.invalidate()
        createRoot.setOnClickListener {
            val bitmap = EncodingUtils.createQRCode(
                "a",
                ScreenUtils.getScreenSize(activity)[0] * 3 / 4,
                ScreenUtils.getScreenSize(activity)[1] / 2
            )
            val p = createImg.layoutParams
            p.width = ScreenUtils.getScreenSize(activity)[0] * 3 / 4
            p.height = ScreenUtils.getScreenSize(activity)[1] / 2
            createImg.layoutParams = p
            createImg.setImageBitmap(bitmap)
        }
    }
}