import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

class Parser {
    static String[] parser() {
        ClassLoader classLoader = Parser.class.getClassLoader();
        URL resource = classLoader.getResource(".properties");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        InputStream fis;
        Properties property = new Properties();
        String[] result = new String[2];
        try {
            fis = resource.openStream();
            property.load(fis);
            result[0] = property.getProperty("token");
            result[1] = property.getProperty("appid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
