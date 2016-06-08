package org.octopus.dashboard;

import org.eclipse.jetty.server.Server;
import org.octopus.dashboard.test.jetty.JettyFactory;
import org.octopus.dashboard.test.spring.Profiles;

public class QuickStartServer {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/recurit";
	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web" };

	public static void main(String[] args) throws Exception {

		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
			System.out.println("[HINT] Hit Enter to reload the application quickly");

			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
