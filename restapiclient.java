package com.internship.codes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Task2 {

    public static void main(String[] args) {
        String city = "Delhi,IN";
        String apiKey = "a1acc05adee25501cf6ec2ff35627daa";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" 
                            + city + "&appid=" + apiKey + "&units=metric";

        try {
            System.out.println("🔗 Connecting to: " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(response.toString());
                String cityName = json.getString("name");
                double temp = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                String weather = json.getJSONArray("weather")
                                     .getJSONObject(0)
                                     .getString("description");

                System.out.println("\n📍 City: " + cityName);
                System.out.println("🌡️ Temperature: " + temp + "°C");
                System.out.println("💧 Humidity: " + humidity + "%");
                System.out.println("⛅ Weather: " + weather);

            } else {
                System.out.println("❌ Error: API returned HTTP " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to fetch weather data: " + e.getMessage());
        }
    }
}
