package org.octopus.dashboard.test;

public class Profiles {
	public static final String ACTIVE_PROFILE = "spring.profiles.active";
	public static final String DEFAULT_PROFILE = "spring.profiles.default";

	public static final String PRODUCTION = "prod";
	public static final String UAT = "uat";
	public static final String DEVELOPMENT = "dev";
	public static final String UNIT_TEST = "test";
	public static final String FUNCTIONAL_TEST = "functional";

	public static void setProfileAsSystemProperty(String profile) {
		System.setProperty(ACTIVE_PROFILE, profile);
	}
}
