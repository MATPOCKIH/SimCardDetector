package anton.aronskiy.simcarddetector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import anton.aronskiy.simcarddetector.providers.SimInfoProvider
import anton.aronskiy.simcarddetector.providers.SimInfoProviderImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simInfoCounter = findViewById<TextView>(R.id.simInfoCounter)
        val simInfoLabel = findViewById<TextView>(R.id.simInfo)

        viewModel.simCards.observe(this) { simCards ->
            simInfoCounter.text = getString(R.string.sim_counter_label, simCards.size)

            val simsInfo = simCards.map {
                getString(R.string.sim_info_label, it.subscriptionId, it.carrierName)
            }
            simInfoLabel.text = simsInfo.joinToString(separator = "\n\n\n")
        }

        requestPermissions()
    }

    private fun requestPermissions(){

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    fetchSubscription()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED -> {
                fetchSubscription()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_PHONE_STATE)
            }
        }
    }

    private fun fetchSubscription() {
        viewModel.getSimCardInfo()
    }

}