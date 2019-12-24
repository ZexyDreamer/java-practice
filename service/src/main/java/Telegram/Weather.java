package Telegram;

import Interfaces.IWeather;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather implements IWeather {
    final double absoluteZero = -273.15;

    public String getWeather(String site) throws IOException {
        URL url;
        try {
            url = new URL(site);
        } catch (MalformedURLException e) {
            return "There is null site";
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in == null){
            return "There is null site";
        }
        String input;
        StringBuilder html = new StringBuilder();
        while((input = in.readLine()) != null) {
            html.append(input);
        }
        in.close();
        return weatherHandler(html.toString());
    }

    public String weatherHandler(String text) {
        if (StringUtils.isBlank(text)) {
            return "There is no text";
        }
        String[] data = text.split(",");
        String weather = "";
        String temperature = "";
        for (String datum : data) {
            if (datum.contains("description")) {
                weather = datum;
            } else if (datum.contains("temp_min")) {
                temperature = datum;
            }
        }
        weather = weatherParser(weather);
        temperature = weatherParser(temperature);
        int temp;
        try {
            temp = (int) Math.round(Double.parseDouble(temperature) + this.absoluteZero);
        } catch (Exception e) {
            return "There is bad data";
        }
        return "Weather is " + weather + " and temperature is " + temp;
    }

    public String weatherParser(String s) {
        String[] data = s.split(":");
        String result;
        try {
            result = data[1].replace("\"", "");
        } catch (Exception e) {
            return "There is bad data";
        }
        return result;
    }
}