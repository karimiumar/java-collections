package com.umar.apps.queue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * A DelayedEvent for {@link java.util.concurrent.DelayQueue }
 */
public final class DelayedEvent implements Delayed {

    private final long startTime;
    private final String eventName;

    public DelayedEvent(String eventName, long delayTime) {
        this.eventName = eventName;
        this.startTime = System.currentTimeMillis() + delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        var diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed that) {
        var result = getDelay(TimeUnit.NANOSECONDS) - that.getDelay(TimeUnit.NANOSECONDS);
        return result == 0 ? 0 : (result > 0 ? 1: -1);
    }

    public long getStartTime() {
        return startTime;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return String.format("""
                DelayedEvent {eventName=%s, startTime=%s }
                """, eventName, startTime);
    }
}
