import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class Test_Weather {
    @Test
    public void testWeatherAPI() throws IOException {
        Weather weather = new Weather();
        String expected = "weather is light intensity drizzle and temperature is 6";
        String actual = weather.getWeather("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSiteParser() {
        Weather weather = new Weather();
        String expected = "There is null site";
        String actual = "";
        try {
            weather.getWeather(null);
        } catch (Exception e) {
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void testWeatherParser() {
        Weather weather = new Weather();
        String expected = "def";
        String[] args = new String[] {"abc:\"def\"", "abc:def", "abc:\"def", "abc:def\""};
        String[] actual = new String[4];
        for (int i = 0; i < args.length; i++) {
            actual[i] = weather.parser(args[i]);
        }
        for (String element : actual) {
            Assert.assertEquals(expected, element);
        }
        Assert.assertEquals("There is bad data", weather.parser(""));
    }

    @Test
    public void testWeatherHandler() {
        Weather weather = new Weather();
        String[] expected = new String[] {"There is no text", "There is bad data"};
        String[] args = new String[] {"", "foo"};
        String[] actual = new String[expected.length];
        for (int i = 0; i < expected.length; i++) {
            actual[i] = weather.weatherHandler(args[i]);
        }
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual[i]);
        }
    }
}