package Interfaces;

import java.io.IOException;

public interface IWeather {
    String getWeather(String site) throws IOException;
    String parser(String s);
    String weatherHandler(String text);
}
