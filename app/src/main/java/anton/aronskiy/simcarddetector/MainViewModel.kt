package anton.aronskiy.simcarddetector

import androidx.lifecycle.*
import anton.aronskiy.simcarddetector.providers.SimInfoProvider
import anton.aronskiy.simcarddetector.utils.launchTimerAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val simInfoProvider: SimInfoProvider) : ViewModel() {

    private var timerAsync: Deferred<Unit>? = null

    private var simCardsLiveData = MutableLiveData<List<SimCardInfo>>()
    var simCards : LiveData<List<SimCardInfo>> = simCardsLiveData


    fun stopDataPulling() {
        timerAsync?.cancel()
    }

    private fun startPulling() {
        timerAsync?.cancel()
        timerAsync = CoroutineScope(Dispatchers.IO)
            .launchTimerAsync(1000) {
                viewModelScope.launch(Dispatchers.Default) {
                    val simInfoList = simInfoProvider.getSimInfo()
                    simCardsLiveData.postValue(simInfoList)
                }
            }
    }

    fun getSimCardInfo() {
        startPulling()
    }

}

data class SimCardInfo(
    val subscriptionId: Int,
    val carrierName: String
)