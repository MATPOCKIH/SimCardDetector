package anton.aronskiy.simcarddetector.providers

import android.Manifest
import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import androidx.annotation.RequiresPermission
import anton.aronskiy.simcarddetector.SimCardInfo

class SimInfoProviderImpl(private val context: Context) : SimInfoProvider {

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun getSimInfo(): List<SimCardInfo> {
        val sm = context.getSystemService(SubscriptionManager::class.java)
        val activeSubscriptionInfoList = sm.activeSubscriptionInfoList
        return activeSubscriptionInfoList.map { it.toSimCardInfo() }
    }

}

fun SubscriptionInfo.toSimCardInfo() : SimCardInfo{
    return SimCardInfo(
        subscriptionId,
        carrierName.toString()
    )
}
