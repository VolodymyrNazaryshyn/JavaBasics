package itstep.learning.ioc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config implements IConfig {
    @Override
    public String getParameter(String name) {
        FileInputStream fileInputStream;
        Properties property = new Properties();

        try {
            fileInputStream = new FileInputStream("config.ini");
            property.load(fileInputStream);
            String value = property.getProperty(name);
            if(value == null || value.isEmpty()) return null;
            return value;
        }
        catch (IOException e) {
            System.err.println("File reading error!");
            return null;
        }
    }
}
