package org.octopus.functional;

import java.net.URL;
import java.sql.Driver;

import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.octopus.dashboard.QuickStartServer;
import org.octopus.dashboard.shared.utils.PropertiesLoader;
import org.octopus.dashboard.test.data.DataFixtures;
import org.octopus.dashboard.test.jetty.JettyFactory;
import org.octopus.dashboard.test.spring.Profiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class BaseFunctionalTestCase {

	protected static String baseUrl;

	protected static Server jettyServer;

	protected static SimpleDriverDataSource dataSource;

	protected static PropertiesLoader propertiesLoader = new PropertiesLoader("classpath:/application.properties",
			"classpath:/application.functional.properties", "classpath:/application.functional-local.properties");

	private static Logger logger = LoggerFactory.getLogger(BaseFunctionalTestCase.class);

	@BeforeClass
	public static void initFunctionalTestEnv() throws Exception {
		baseUrl = propertiesLoader.getProperty("baseUrl");

		boolean isEmbedded = new URL(baseUrl).getHost().equals("localhost")
				&& propertiesLoader.getBoolean("embeddedForLocal");

		if (isEmbedded) {
			startJettyOnce();
		}

		buildDataSourceOnce();
		reloadSampleData();
	}

	protected static void startJettyOnce() throws Exception {
		if (jettyServer == null) {

			Profiles.setProfileAsSystemProperty(Profiles.FUNCTIONAL_TEST);

			jettyServer = JettyFactory.createServerInSource(new URL(baseUrl).getPort(), QuickStartServer.CONTEXT);
			JettyFactory.setTldJarNames(jettyServer, QuickStartServer.TLD_JAR_NAMES);
			jettyServer.start();

			logger.info("Jetty Server started at {}", baseUrl);
		}
	}

	protected static void buildDataSourceOnce() throws ClassNotFoundException {
		if (dataSource == null) {
			dataSource = new SimpleDriverDataSource();
			dataSource.setDriverClass(
					(Class<? extends Driver>) Class.forName(propertiesLoader.getProperty("jdbc.driver")));
			dataSource.setUrl(propertiesLoader.getProperty("jdbc.url"));
			dataSource.setUsername(propertiesLoader.getProperty("jdbc.username"));
			dataSource.setPassword(propertiesLoader.getProperty("jdbc.password"));
		}
	}

	protected static void reloadSampleData() throws Exception {
		String dbType = propertiesLoader.getProperty("db.type", "h2");
		DataFixtures.executeScript(dataSource, "classpath:data/" + dbType + "/cleanup-data.sql",
				"classpath:data/" + dbType + "/import-data.sql");
	}
}