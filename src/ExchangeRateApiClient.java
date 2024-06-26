package com.example.currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeRateApiClient {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void load(final LoadCallback callback) {
        executor.execute(() -> {
            try {
                String apiKey = ""; //Write your own API Key
                String endpoint = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";
                URL url = new URI(endpoint).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                HashMap<String, Double> map = new HashMap<>();
                String date="";
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("time_last_update_utc")) {
                        date = line.substring(25, line.length() - 7) + "UTC";
                    } else if (line.contains("conversion_rates")) {
                        while ((line = reader.readLine()) != null) {
                            if (line.length() < 3) break;
                            String ctry = line.substring(3, 6);
                            double cur = Double.parseDouble(line.substring(8, line.length() - 1));
                            map.put(ctry, cur);
                        }
                    }
                }
                reader.close();
                connection.disconnect();

                // Call the callback method to notify that loading is complete
                callback.onLoaded(map,date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface LoadCallback {
        void onLoaded(HashMap<String, Double> loadedMap, String date);
    }
}
