package Main;

import Interfaces.IParser;

import java.util.Properties;

public class Parser implements IParser {
    public static Properties parser() {
        return IParser.parser();
    }
}
