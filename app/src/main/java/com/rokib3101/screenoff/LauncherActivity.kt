package com.rokib3101.screenoff

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
        val isDeviceAdminActive = dpm.isAdminActive(adminComponent)

        if (isDeviceAdminActive) {
            try {
                dpm.lockNow()
                finish()
                return
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val locked = ScreenOffAccessibilityService.lockScreen()
        if (locked) {
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
