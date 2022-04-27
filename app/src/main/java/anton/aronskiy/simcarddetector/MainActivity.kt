package anton.aronskiy.simcarddetector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE = 144
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getSIMInfo()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getSIMInfo()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_PHONE_STATE)
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getSIMInfo() {
        val sm = getSystemService(SubscriptionManager::class.java)
        val  activeSubscriptionInfoList = sm.activeSubscriptionInfoList
        for (subscriptionInfo in activeSubscriptionInfoList) {
            val carrierName = subscriptionInfo.getCarrierName()
            val displayName = subscriptionInfo.getDisplayName()
            val mcc = subscriptionInfo.getMcc()
            val mnc = subscriptionInfo.getMnc()
            val subscriptionInfoNumber = subscriptionInfo.getNumber()
            Log.d("Anton","Finish")
        }

        val mTelephonyMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val imei: String = mTelephonyMgr.getDeviceId()
    }
}