package net.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.layout_dialog_scan_result.view.*
import kotlinx.android.synthetic.main.layout_dialog_scan_type.view.*
import kotlinx.android.synthetic.main.layout_fragment_sacn.*
import net.HistoryEntity
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

    val typeDlg: Dialog? = null
    val resultDlg: Dialog? = null

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
            showTypeDlg()
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
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null || result.contents == null)
            Toast.makeText(activity, "no data", Toast.LENGTH_SHORT).show()
        else {
            showResultDlg(result.contents)
        }
    }

    private fun showResultDlg(content: String) {
        var dlg: Dialog? = null
        val view = layoutInflater.inflate(R.layout.layout_dialog_scan_result, null)
        dlg = AlertDialog.Builder(activity).setView(view).create()
        val p: ViewGroup.LayoutParams = view.resultTv.layoutParams
        p.height = ScreenUtils.getScreenSize(activity)[0] / 4
        p.width = ScreenUtils.getScreenSize(activity)[1]
        view.resultTv.layoutParams = p
        view.resultTv.text = content
        view.resultClose.setOnClickListener {
            dlg.dismiss()
        }
        view.resultCopy.setOnClickListener {
            val time = System.currentTimeMillis().toString()
            val entity: HistoryEntity = HistoryEntity()
            entity.content = content
            entity.type = "scan"
            entity.time = time
            MMKV.defaultMMKV()?.encode("time", time)
            MMKV.defaultMMKV()?.encode(time, entity)
            copy(content)
            dlg.dismiss()
        }
        dlg.show()
    }

    private fun copy(content: String) {
        val clipManager: ClipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", content)
        clipManager.setPrimaryClip(mClipData)
        Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
    }

    private fun showTypeDlg() {
        var dlg: Dialog? = null
        val view = layoutInflater.inflate(R.layout.layout_dialog_scan_type, null)
        dlg = AlertDialog.Builder(activity).setView(view).create()
        view.typeQr.setOnClickListener {
            doScan(IntentIntegrator.QR_CODE_TYPES, R.string.scan_qrcode)
            dlg.dismiss()
        }
        view.typeBar.setOnClickListener {
            doScan(IntentIntegrator.ONE_D_CODE_TYPES, R.string.scan_barcode)
            dlg.dismiss()
        }
        dlg.show()
    }
}