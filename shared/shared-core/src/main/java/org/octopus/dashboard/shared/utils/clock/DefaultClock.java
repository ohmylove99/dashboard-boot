package org.octopus.dashboard.shared.utils.clock;

import java.util.Date;

public class DefaultClock implements IClock {

	@Override
	public Date getCurrentDate() {
		return new Date();
	}

	@Override
	public long getCurrentTimeInMillis() {
		return System.currentTimeMillis();
	}

}
