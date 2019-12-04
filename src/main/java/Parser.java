import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

class Parser {
    static Properties parser() {
        ClassLoader classLoader = Parser.class.getClassLoader();
        URL resource = classLoader.getResource(".properties");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        InputStream fis;
        Properties property = new Properties();
        try {
            fis = resource.openStream();
            property.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }
}
