package org.octopus.selenium;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


public class SeleniumSnapshotRule extends TestWatcher {

	private final Selenium2 s;

	public SeleniumSnapshotRule(Selenium2 s) {
		this.s = s;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		String basePath = "target/screenshot/";
		String outputFileName = description.getClassName() + "_" + description.getMethodName() + ".png";
		s.snapshot(basePath, outputFileName);
	}
}
