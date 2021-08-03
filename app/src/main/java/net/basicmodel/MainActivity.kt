package net.basicmodel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.layout_bottom.*
import net.fragment.CreateFragment
import net.fragment.HistoryFragment
import net.fragment.ScanFragment

class MainActivity : AppCompatActivity() {

    var scanFragment: ScanFragment? = null
    var createFragment: CreateFragment? = null
    var historyFragment: HistoryFragment? = null

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
        showPosition(0)
        initView()
    }

    fun requestPermissions() {
        if (checkPermission(permissions[0]) && checkPermission(permissions[1])) {

        } else {
            ActivityCompat.requestPermissions(this, permissions, 321)
        }
    }

    private fun checkPermission(per: String): Boolean {
        return ContextCompat.checkSelfPermission(this, per) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    Log.i("xxxxxxH", "获取权限成功")
                }
            }
        }
    }

    private fun initView() {
        scan.setOnClickListener { showPosition(0) }
        create.setOnClickListener { showPosition(1) }
        history.setOnClickListener { showPosition(2) }
    }

    private fun showPosition(position: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        hideAll(ft)
        if (position == 0) {
            scanFragment = fm.findFragmentByTag("scan") as ScanFragment?
            if (scanFragment == null) {
                scanFragment = ScanFragment()
                ft.add(R.id.content, scanFragment!!, "scan")
            } else {
                ft.show(scanFragment!!)
            }
            if (historyFragment != null) {
                ft.remove(historyFragment!!)
            }
        }

        if (position == 1) {
            createFragment = fm.findFragmentByTag("create") as CreateFragment?
            if (createFragment == null) {
                createFragment = CreateFragment()
                ft.add(R.id.content, createFragment!!, "create")
            } else {
                ft.show(createFragment!!)
            }
            if (historyFragment != null) {
                ft.remove(historyFragment!!)
            }

        }

        if (position == 2) {
            historyFragment = fm.findFragmentByTag("history") as HistoryFragment?
            if (historyFragment == null) {
                historyFragment = HistoryFragment()
                ft.add(R.id.content, historyFragment!!, "history")
            } else {
                ft.show(historyFragment!!)
            }

        }
        ft.commit()
    }

    private fun hideAll(ft: FragmentTransaction) {
        if (scanFragment != null) {
            ft.hide(scanFragment!!)
        }
        if (createFragment != null) {
            ft.hide(createFragment!!)
        }
        if (historyFragment != null) {
            ft.hide(historyFragment!!)
        }
    }
}