package org.octopus.dashboard.shared.utils.clock;

public class ClockFactory {
	public static synchronized IClock getClock() {
		return new DefaultClock();
	}
}
