package events

interface EventsStatistic {

    fun incEvent(name: String)

    fun getEventStatisticByName(name: String): LongArray

    fun getAllEventStatistic(): Map<String, LongArray>

    fun printStatistic() {
        val allStat = getAllEventStatistic()
        for ((name, stat) in allStat) {
            print("Event: $name rpm: $allStat")
        }
    }
}