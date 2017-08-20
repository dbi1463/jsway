/* ApplicationRestarter.java created on 2013/3/12
 *
 * Copyright (C) 2011. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway;

import static java.lang.Runtime.getRuntime;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static java.lang.System.out;
import static java.lang.management.ManagementFactory.getRuntimeMXBean;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The class that can close the running application and start another application.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class ApplicationRestarter {

	private static final String COMMADN_SEPARATOR = " ";
	private static final String JAVA_BINARY = "/bin/java";
	private static final String JAR_FILE_EXTENSION = ".jar";
	private static final String JAVA_HOME_PROPERTY = "java.home";
	private static final String JAVA_CLASS_PATH_PROPERTY = "java.class.path";

	/**
	 * Exit the current process and launch another Java application. The application entry
	 * can be a JAR file or a class name (full name).
	 * 
	 * @param entry the application entry
	 * @param arguments the startup arguments
	 * @param runBeforeRestart a hook to be executed before terminating the current process
	 * @throws IOException if any error occurs
	 */
	public void exitAndLaunchApplication(String entry, String[] arguments, Runnable runBeforeRestart) throws IOException {
		try {
			// Get the java binary path
			String javaPath = surroundQuotes(getProperty(JAVA_HOME_PROPERTY) + JAVA_BINARY);

			// Get VM arguments
			List<String> vmArguments = getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// Ignore agent argument
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(COMMADN_SEPARATOR);
				}
			}

			// Initialize the command to execute
			final StringBuffer commandBuffer = new StringBuffer(javaPath + " " + vmArgsOneLine);
			if (entry.endsWith(JAR_FILE_EXTENSION)) {
				commandBuffer.append("-jar " + surroundQuotes(new File(entry).getPath()));
			} else {
				commandBuffer.append("-cp " + surroundQuotes(getProperty(JAVA_CLASS_PATH_PROPERTY)) + " " + entry);
			}
			for (String argument : arguments) {
				commandBuffer.append(COMMADN_SEPARATOR);
				commandBuffer.append(argument);
			}

			// Execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						String command = commandBuffer.toString();
						out.println(command);
						getRuntime().exec(command);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			// Execute some custom code before restarting
			if (runBeforeRestart != null) {
				runBeforeRestart.run();
			}
			exit(0);
		} catch (Exception e) {
			throw new IOException("Error while trying to restart the application", e);
		}
	}

	/**
	 * Surround quotes on the specified if the text contains space.
	 * 
	 * @param text the text to be checked
	 * @return quotes surrounded text if needed
	 */
	private String surroundQuotes(String text) {
		if(text.contains(" ")) {
			text = "\"" + text + "\"";
		}
		return text;
	}
}
