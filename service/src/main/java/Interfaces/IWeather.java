package Interfaces;

import java.io.IOException;

public interface IWeather {
    String getWeather(String site) throws IOException;
    String weatherParser(String s);
    String weatherHandler(String text);
}
