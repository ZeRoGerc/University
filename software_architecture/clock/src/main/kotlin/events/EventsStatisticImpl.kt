package events

import clock.Clock
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.SECONDS

class EventsStatisticImpl(private val clock: Clock) : EventsStatistic {

    val stats = mutableMapOf<String, Statistic>()

    override fun incEvent(name: String) {
        insertKeyIfNotPresent(name)

        stats[name]?.inc(currentMinute())
    }

    override fun getEventStatisticByName(name: String): LongArray {
        insertKeyIfNotPresent(name)

        return stats[name]?.getRpm(currentMinute()) ?: LongArray(MINUTES_IN_HOUR)
    }

    override fun getAllEventStatistic(): Map<String, LongArray> {
        return stats.mapValues { it -> it.value.getRpm(currentMinute()) }
    }

    private fun currentMinute() = SECONDS.toMinutes(clock.now().epochSecond)

    private fun insertKeyIfNotPresent(name: String) {
        if (!stats.containsKey(name)) {
            stats[name] = Statistic()
        }
    }

    class Statistic {

        private val rpm = LongArray(MINUTES_IN_HOUR)

        private var currentTime = 0L

        fun inc(time: Long) {
            moveToTime(time)

            currentTime = time
            rpm[(currentTime % MINUTES_IN_HOUR).toInt()]++
        }

        fun getRpm(time: Long): LongArray {
            moveToTime(time)

            val resRpm = LongArray(MINUTES_IN_HOUR)

            for (it in 0..MINUTES_IN_HOUR - 1) {
                resRpm[it] = rpm[((currentTime + it + 1) % MINUTES_IN_HOUR).toInt()]
            }

            return resRpm
        }


        private fun moveToTime(time: Long) {
            // zeroing all cells from currentTime to time
            var it = currentTime + 1
            while (it <= time && it - currentTime <= MINUTES_IN_HOUR) {
                rpm[(it % MINUTES_IN_HOUR).toInt()] = 0
                it++
            }

            currentTime = time
        }
    }

    companion object {
        val MINUTES_IN_HOUR = HOURS.toMinutes(1).toInt()
    }
}