package Handlers;

import Database.Database;
import Interfaces.IWeatherHandler;
import Telegram.Weather;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeolocationHandler implements IWeatherHandler {
    @Override
    public String weatherHandle(String message, String app_id, Database database, String chat_id) throws IOException {
        Weather weather = new Weather();
        String latitude = "";
        String longitude = "";
        int offsetLongitude = 10;
        int offsetLatitude = 9;
        String location = database.get(chat_id);
        Pattern longitudePattern = Pattern.compile("(longitude=)(\\d*\\.\\d*)");
        Matcher longitudeMatcher = longitudePattern.matcher(location);
        while (longitudeMatcher.find()) {
            String temperature = location.substring(longitudeMatcher.start(), longitudeMatcher.end());
            longitude = temperature.substring(offsetLongitude);
        }
        Pattern latitudePattern = Pattern.compile("(latitude=)(\\d*\\.\\d*)");
        Matcher latitudeMatcher = latitudePattern.matcher(location);
        while (latitudeMatcher.find()) {
            String temperature = location.substring(latitudeMatcher.start(), latitudeMatcher.end());
            latitude = temperature.substring(offsetLatitude);
        }
        if (StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
            return "You should change your location in settings before it.";
        } else {
            String appid = "&APPID=" + app_id;
            String s = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + appid;
            return weather.getWeather(s);
        }
    }

}
