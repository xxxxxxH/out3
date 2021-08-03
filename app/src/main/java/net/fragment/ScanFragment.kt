package net.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.layout_fragment_sacn.*
import net.basicmodel.R
import net.utils.ScreenUtils


/**
 * Copyright (C) 2021,2021/8/3, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class ScanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_sacn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val p = scanImg.layoutParams
        p.width = ScreenUtils.getScreenSize(activity)[0] * 3 / 4
        p.height = ScreenUtils.getScreenSize(activity)[1] / 2
        scanImg.layoutParams = p
        scanRoot.setOnClickListener {
            doScan(IntentIntegrator.QR_CODE_TYPES, R.string.scan_qrcode)
        }
    }

    private fun doScan(scannerType: Collection<String>, promptId: Int) {
        val integrator: IntentIntegrator = IntentIntegrator.forSupportFragment(this)
        // use forSupportFragment or forFragment method to use fragments instead of activity
        integrator.setDesiredBarcodeFormats(scannerType)
        integrator.setPrompt(activity?.getString(promptId))
        integrator.setCameraId(0) // Use a specific camera of the device

        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

    }
}