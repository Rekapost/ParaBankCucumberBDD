package utilities.log4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class Loggerload {
	
	// LOG4J LOGGER CONFIGURATION
	private static final Logger logger = LogManager.getLogger(Loggerload.class);	  // log4j

	public static void info(String message) {
		logger.info(message);
	}
	public static void info(int num) {
		logger.info(num);
	}
	public static void warn(String message) {
		logger.warn(message);
	}
	public static void error(String message) {
		logger.error(message);
	}
	public static void debug(String message) {
		logger.debug(message);
	}
	
}
