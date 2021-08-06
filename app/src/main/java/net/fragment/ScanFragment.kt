package net.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.layout_fragment_sacn.*
import net.basicmodel.R


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
        typeQr.setOnClickListener {
            doScan(IntentIntegrator.QR_CODE_TYPES, R.string.scan_qrcode)
        }
        typeBar.setOnClickListener {
            doScan(IntentIntegrator.ONE_D_CODE_TYPES, R.string.scan_barcode)
        }
        resultCopy.setOnClickListener {
            copy(resultTv.text.toString())
        }
    }

    private fun doScan(scannerType: Collection<String>, promptId: Int) {
        val integrator: IntentIntegrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(scannerType)
        integrator.setPrompt(activity?.getString(promptId))
        integrator.setCameraId(0)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null || result.contents == null)
            Toast.makeText(activity, "no data", Toast.LENGTH_SHORT).show()
        else {
            resultTv.text = result.contents
        }
    }


    private fun copy(content: String) {
        if (TextUtils.isEmpty(content) || TextUtils.equals(content, "No Data"))
            return
        val clipManager: ClipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", content)
        clipManager.setPrimaryClip(mClipData)
        Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
    }

}