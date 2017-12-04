package ig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;

public class WeatherImpl implements WeatherService{
	private static final String WEATHER_URL = "http://samples.openweathermap.org/data/2.5/weather?";

    /** Sample app ID. */
    private static final String appId = "ca7345b4a1ef8c037f7749c09fcbf808";

    /** {@inheritDoc}. */
    @Override 
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Weather Service is initialized!");
    }

    /** {@inheritDoc}. */
    @Override 
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Weather Service is started!");
    }

    /** {@inheritDoc}. */
    @Override 
    public void cancel(ServiceContext ctx) {
        System.out.println("Weather Service is stopped!");
    }

    /** {@inheritDoc}. */
    @Override 
    public String getCurrentTemperature(String cityName, String countryCode) throws Exception {
        System.out.println("Starting");
        System.out.println(">>> Requested weather forecast [city=" 
            + cityName + ", countryCode=" + countryCode + "]");

        String connStr = WEATHER_URL + "q=" + cityName + ","
            + countryCode + "&appid=" + appId;

        URL url = new URL(connStr);

        HttpURLConnection conn = null;

        try {
            // Connecting to the weather service.
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            // Read data from the weather server.
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {

                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null)
                    builder.append(line);

                return builder.toString();
            }
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }
    public static void main(String[] args) throws Exception {
        try (Ignite ignite = Ignition.start()) {

            // Deploying a single instance of the Weather Service 
            // in the whole cluster.
            ignite.services().deployClusterSingleton("WeatherService",
               new WeatherImpl());

            // Requesting current weather for London.
            WeatherService service = ignite.services().service("WeatherService");
            System.out.println("Getting Data");
            String forecast = service.getCurrentTemperature("Kolkata", "India");

            System.out.println("Weather forecast in London:" + forecast);
        }
    }
}

	interface WeatherService extends Service {
	    /**
	     * Get a current temperature for a specific city in the world.
	     *
	     * @param countryCode Country code (ISO 3166 country codes).
	     * @param cityName City name.
	     * @return Current temperature in the city in JSON format.
	     * @throws Exception if an exception happened.
	     */
	    String getCurrentTemperature(String countryCode, String cityName)
	        throws Exception;
	}

