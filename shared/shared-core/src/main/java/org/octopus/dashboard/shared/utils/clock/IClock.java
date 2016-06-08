package org.octopus.dashboard.shared.utils.clock;

import java.util.Date;

public interface IClock {
	Date getCurrentDate();

	long getCurrentTimeInMillis();
}
