package Handlers;

import Database.Database;
import Interfaces.IWeatherHandler;
import Telegram.Weather;

import java.io.IOException;

public class TapingCityHandler implements IWeatherHandler {
    @Override
    public String weatherHandle(String message, String app_id, Database database, String chat_id) throws IOException {
        int offsetCity = 5;
        String city = message.substring(offsetCity);
        if (city.equals("")) {
            return "Your should write \"/city YOURCITY\"";
        } else {
            Weather weather = new Weather();
            String appid = "&APPID=" + app_id;
            String s = "http://api.openweathermap.org/data/2.5/weather?q=" + city + appid;
            return weather.getWeather(s);
        }
    }
}
