package com.example.battery_info_demo

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity(){
    private val Channel = "com.example.battery"
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, Channel).setMethodCallHandler {
            call, result ->
            if (call.method == "getBatteryLevel"){
                val batteryLevel = getBatterylevel()
                if(batteryLevel != -1){
                    result.success(batteryLevel)
                }
                else{
                    result.error("UNAVAILABLE", "Battery not now", null)
                }
            }
            else{
                result.notImplemented()
            }
        }
    }
    private fun getBatterylevel(): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
            ifilter -> applicationContext.registerReceiver(null, ifilter)
        }
        return batteryStatus?.let {
            val level: Int = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            (level * 100) / scale
        }?: -1
    }
}
