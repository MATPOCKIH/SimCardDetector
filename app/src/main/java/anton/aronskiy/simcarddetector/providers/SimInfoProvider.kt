package anton.aronskiy.simcarddetector.providers

import anton.aronskiy.simcarddetector.SimCardInfo

interface SimInfoProvider {
    fun getSimInfo() : List<SimCardInfo>
}