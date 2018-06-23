package events

import clock.SetableClock
import events.EventsStatisticImpl.Companion.MINUTES_IN_HOUR
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.time.Instant.ofEpochSecond
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MINUTES

class EventsStatisticImplTest {

    val clock: SetableClock = SetableClock(ofEpochSecond(0))

    lateinit var eventStatistic: EventsStatistic

    @Before
    fun setUp() {
        eventStatistic = EventsStatisticImpl(clock)
    }

    @Test
    fun `getEventStatisticByName should return empty if no events`() {
        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).containsOnly(0)
    }

    @Test
    fun `getEventStatisticByName should count single event`() {
        eventStatistic.incEvent(EVENT1)
        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isEqualTo(createArray(
                Pair(ago(0), 1)
        ))
    }

    @Test
    fun `getEventStatisticByName should count single event if not expired`() {
        eventStatistic.incEvent(EVENT1)

        clock.now = ofEpochSecond(MINUTES.toSeconds(5))

        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isEqualTo(createArray(
                Pair(ago(5), 1)
        ))
    }

    @Test
    fun `getEventStatisticByName should not count single event if expired`() {
        eventStatistic.incEvent(EVENT1)

        clock.now = ofEpochSecond(HOURS.toSeconds(1))
        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).containsOnly(0)
    }

    @Test
    fun `getEventStatisticByName should count two same events in the same time`() {
        eventStatistic.incEvent(EVENT1)
        eventStatistic.incEvent(EVENT1)
        clock.now = ofEpochSecond(MINUTES.toSeconds(1))

        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isEqualTo(createArray(
                Pair(ago(1), 2))
        )
    }

    @Test
    fun `getEventStatisticByName should count two same events in the different times`() {
        eventStatistic.incEvent(EVENT1)
        clock.now = ofEpochSecond(MINUTES.toSeconds(2))
        eventStatistic.incEvent(EVENT1)
        eventStatistic.incEvent(EVENT1)

        assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isEqualTo(createArray(
                Pair(ago(2), 1),
                Pair(ago(0), 2)
        ))
    }

    @Test
    fun `getAllEventStatistic complex test`() {
        eventStatistic.incEvent(EVENT1)

        clock.now = ofEpochSecond(MINUTES.toSeconds(30))
        eventStatistic.incEvent(EVENT2)
        eventStatistic.incEvent(EVENT1)
        eventStatistic.incEvent(EVENT1)

        clock.now = ofEpochSecond(HOURS.toSeconds(1))
        eventStatistic.incEvent(EVENT2)
        eventStatistic.incEvent(EVENT2)
        eventStatistic.incEvent(EVENT1)

        val stats = eventStatistic.getAllEventStatistic()

        assertThat(stats[EVENT1]).isEqualTo(createArray(
                Pair(ago(0), 1),
                Pair(ago(30), 2)
        ))
        assertThat(stats[EVENT2]).isEqualTo(createArray(
                Pair(ago(0), 2),
                Pair(ago(30), 1)
        ))
    }

    private fun createArray(vararg pairs: Pair<Int, Long>): LongArray {
        val res = LongArray(MINUTES_IN_HOUR)
        pairs.forEach { (t, c) -> res[t] = c }
        return res
    }

    private fun ago(t: Int) = MINUTES_IN_HOUR - 1 - t

    companion object {
        val EVENT1 = "event1"
        val EVENT2 = "event2"
    }
}