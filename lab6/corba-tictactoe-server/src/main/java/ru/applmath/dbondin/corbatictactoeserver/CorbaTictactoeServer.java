package ru.applmath.dbondin.corbatictaktoeserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorbaTictaktoeServer {

	public static final String HTTP_TARGET_IDL = "/tic-tac-toe.idl";

	public static final String HTTP_TARGET_IOR = "/tic-tac-toe.ior";

	public static final String HTTP_TARGET_ROOT = "/";

	private static final Logger LOGGER = LoggerFactory.getLogger(CorbaTictaktoeServer.class);

	private final int httpServerPort;

	private final Map<String, HttpProcessor> httpProcessors;

	private String iorString = null;

	private static final String MAVEN_PROPERTIES_RESOURCE_NAME = "/META-INF/maven/ru.applmath.dbondin/corba-tictaktoe-server/pom.properties";

	private String version = "UNKNOWN";
	
	private void loadMavenProperties() {
		InputStream is  = null;
		try {
			is = getClass().getResourceAsStream(MAVEN_PROPERTIES_RESOURCE_NAME);
			if(is != null) {
				Properties props = new Properties();
				props.load(is);
				version = props.getProperty("version");
			}
		} catch (Throwable t) {
			if (is != null) {
				try {
					is.close();
				} catch (Throwable t2) {
					/* Ignore */
				}
			}
		}
	}

	public CorbaTictaktoeServer(int httpServerPort) {
		this.httpServerPort = httpServerPort;

		httpProcessors = new HashMap<String, HttpProcessor>();
		httpProcessors.put(HTTP_TARGET_IDL, new HttpProcessor() {
			public void printData(PrintWriter writer) {
				InputStream is = null;
				try {
					is = getClass().getResourceAsStream("/tic-tac-toe.idl");
					if (is != null) {
						byte[] buffer = new byte[1024];
						while (true) {
							int bytesRead = is.read(buffer);
							if (bytesRead == -1) {
								break;
							}
							writer.write(new String(buffer, 0, bytesRead));
						}
					}
				} catch (Throwable t) {
					if (is != null) {
						try {
							is.close();
						} catch (Throwable t2) {
							/* Ignore */
						}
					}
				}
			}

			public String getContentType() {
				return "text/plain";
			}
		});
		httpProcessors.put(HTTP_TARGET_IOR, new HttpProcessor() {
			public void printData(PrintWriter writer) {
				writer.print(iorString);
			}

			public String getContentType() {
				return "text/plain";
			}
		});
		httpProcessors.put(HTTP_TARGET_ROOT, new HttpProcessor() {
			public void printData(PrintWriter writer) {
				writer.println("<html>");
				writer.println(" <head>");
				writer.println(" <title>CORBA TicTacToe Server</title>");
				writer.println(" </head>");
				writer.println(" <body>");
				writer.println("  <div><h1>CORBA TicTacToe Server</h1></div>");
				writer.println("  <div><h2>Version: " + version + "</h2></div>");
				writer.println("  <div>Please use one of the following URLs:</div>");
				writer.println("  <div><a href='" + HTTP_TARGET_IDL + "'>TicTacToe IDL file</a></div>");
				writer.println("  <div><a href='" + HTTP_TARGET_IOR + "'>EntryPoint IOR file</a></div>");
				writer.println(" </body>");
				writer.println("</html>");
			}

			public String getContentType() {
				return "text/html";
			}
		});
		
		loadMavenProperties();
	}

	public boolean startCorbaServer() {

		LOGGER.info("Starting CORBA server");

		try {

			final ORB orb = ORB.init(new String[] {}, null);
			final POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

			rootPOA.the_POAManager().activate();

			final GameImpl entryPointImpl = new GameImpl();

			final org.omg.CORBA.Object entryPointRef = rootPOA.servant_to_reference(entryPointImpl);

			iorString = orb.object_to_string(entryPointRef);

			LOGGER.info("EntryPoint: {}", iorString);

			LOGGER.info("Starting ORB thread");

			new Thread(new Runnable() {
				public void run() {
					orb.run();
				}
			}, "ORB-Thread").start();

		} catch (Throwable t) {
			LOGGER.error("Error starting CORBA server", t);
			return false;
		}

		return true;
	}

	public boolean startHttpServer() {

		LOGGER.info("Starting HTTP server (port {})", httpServerPort);

		final Server server = new Server(httpServerPort);
		server.setHandler(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest, HttpServletRequest request,
					HttpServletResponse response) throws IOException, ServletException {

				LOGGER.debug("Searching processor for target: '{}'", target);

				HttpProcessor processor = httpProcessors.get(target);

				LOGGER.debug("Processor for target '{}' {}", target, (processor != null) ? "found" : "not found");

				if (processor != null) {
					response.setContentType(processor.getContentType());
					processor.printData(response.getWriter());
					response.setStatus(HttpServletResponse.SC_OK);
					baseRequest.setHandled(true);
				}
			}
		});

		try {
			server.start();
		} catch (Throwable t) {
			LOGGER.error("");
		}
		return true;
	}

	public void start() {
		LOGGER.info("*** Starting CorbaTictaktoeServer ***");
		if (!startCorbaServer()) {
			LOGGER.error("Failed to start CORBA server. Exiting.");
			System.exit(1);
		}
		if (!startHttpServer()) {
			LOGGER.error("Failed to start HTTP server. Exiting.");
			System.exit(1);
		}
		LOGGER.info("*** CorbaTictaktoeServer is running now ***");
	}

	public static void main(String[] args) {

		if (args.length < 1) {
			LOGGER.error("HTTP server port is required");
			System.exit(1);
		}
		int httpServerPort = -1;
		try {
			httpServerPort = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			LOGGER.error("Bad HTTP server port specified", ex);
			System.exit(1);
		}

		CorbaTictaktoeServer corbaTictaktoeServer = new CorbaTictaktoeServer(httpServerPort);
		corbaTictaktoeServer.start();
	}
}
