package com.rokib3101.screenoff

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var btnEnableAccessibility: Button
    private lateinit var btnEnableAdmin: Button
    private lateinit var btnPowerMenu: Button
    private lateinit var btnVolumeControl: Button
    private lateinit var btnLockNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        btnEnableAccessibility = findViewById(R.id.btnEnableAccessibility)
        btnEnableAdmin = findViewById(R.id.btnEnableAdmin)
        btnPowerMenu = findViewById(R.id.btnPowerMenu)
        btnVolumeControl = findViewById(R.id.btnVolumeControl)
        btnLockNow = findViewById(R.id.btnLockNow)

        btnEnableAccessibility.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        btnEnableAdmin.setOnClickListener {
            try {
                val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                    putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
                    putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.device_admin_description))
                }
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnPowerMenu.setOnClickListener {
            val shown = ScreenOffAccessibilityService.showPowerMenu()
            if (!shown) {
                Toast.makeText(this, R.string.toast_accessibility_required, Toast.LENGTH_LONG).show()
                btnEnableAccessibility.performClick()
            }
        }

        btnVolumeControl.setOnClickListener {
            try {
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                audioManager.adjustSuggestedStreamVolume(
                    AudioManager.ADJUST_SAME,
                    AudioManager.USE_DEFAULT_STREAM_TYPE,
                    AudioManager.FLAG_SHOW_UI,
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnLockNow.setOnClickListener {
            lockDevice()
        }

        setupDynamicShortcuts()
    }

    private fun setupDynamicShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            try {
                val shortcutManager = getSystemService(ShortcutManager::class.java)
                val powerMenuShortcut = ShortcutInfo.Builder(this, "power_menu")
                    .setShortLabel(getString(R.string.shortcut_power_menu))
                    .setLongLabel(getString(R.string.shortcut_power_menu))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_power))
                    .setIntent(
                        Intent(this, PowerMenuActivity::class.java).apply {
                            action = Intent.ACTION_VIEW
                        }
                    )
                    .build()

                val volumeControlShortcut = ShortcutInfo.Builder(this, "volume_control")
                    .setShortLabel(getString(R.string.shortcut_volume_control))
                    .setLongLabel(getString(R.string.shortcut_volume_control))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_volume))
                    .setIntent(
                        Intent(this, VolumeControlActivity::class.java).apply {
                            action = Intent.ACTION_VIEW
                        }
                    )
                    .build()

                val settingsShortcut = ShortcutInfo.Builder(this, "settings")
                    .setShortLabel(getString(R.string.shortcut_settings))
                    .setLongLabel(getString(R.string.shortcut_settings))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_settings))
                    .setIntent(
                        Intent(this, MainActivity::class.java).apply {
                            action = Intent.ACTION_VIEW
                        }
                    )
                    .build()

                shortcutManager.dynamicShortcuts = listOf(powerMenuShortcut, volumeControlShortcut, settingsShortcut)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun lockDevice() {
        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
        if (dpm.isAdminActive(adminComponent)) {
            dpm.lockNow()
        } else {
            val locked = ScreenOffAccessibilityService.lockScreen()
            if (!locked) {
                btnEnableAdmin.performClick()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun updateStatus() {
        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponent = ComponentName(this, DeviceAdminReceiver::class.java)
        val isDeviceAdminActive = dpm.isAdminActive(adminComponent)
        val isAccessibilityActive = ScreenOffAccessibilityService.instance != null

        if (isDeviceAdminActive || isAccessibilityActive) {
            statusTextView.text = getString(R.string.status_active)
            if (isDeviceAdminActive) {
                btnEnableAdmin.text = getString(R.string.admin_enabled)
            }
            if (isAccessibilityActive) {
                btnEnableAccessibility.text = getString(R.string.accessibility_enabled)
            }
        } else {
            statusTextView.text = getString(R.string.status_inactive)
            btnEnableAdmin.text = getString(R.string.enable_device_admin)
            btnEnableAccessibility.text = getString(R.string.enable_accessibility)
        }
    }
}
