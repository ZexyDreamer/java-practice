import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class Weather {
    String getWeather(String site) throws IOException {
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
        String input;
        StringBuilder html = new StringBuilder();
        if (in == null){
            return "There is null site";
        }
        while((input = in.readLine()) != null) {
            html.append(input);
        }
        in.close();
        return weatherHandler(html.toString());
    }

    String weatherHandler(String text) {
        if (StringUtils.isBlank(text)) {
            return "There is no text";
        }
        double absoluteZero = -273.15;
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
        weather = parser(weather);
        temperature = parser(temperature);
        int temp;
        try {
            temp = (int) Math.round(Double.parseDouble(temperature) + absoluteZero);
        } catch (Exception e) {
            return "There is bad data";
        }
        return "Weather is " + weather + " and temperature is " + temp;
    }

    String parser(String s) {
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