package com.rokib3101.screenoff

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val locked = ScreenOffAccessibilityService.lockScreen()
        
        if (!locked) {
            try {
                val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
                val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
                if (dpm.isAdminActive(adminComponent)) {
                    dpm.lockNow()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        finish()
    }
}
