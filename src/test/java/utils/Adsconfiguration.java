package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Adsconfiguration {

    public Properties pro;
    public String url;
    public String username;
    public String password;

    public Adsconfiguration() {
        try {
            // Load properties file
            File file = new File(System.getProperty("user.dir") + "/src/test/resources/global.properties");
            if (!file.exists()) {
                throw new RuntimeException("global.properties not found at: " + file.getAbsolutePath());
            }

            FileInputStream fis = new FileInputStream(file);
            pro = new Properties();
            pro.load(fis);

            // Get env  from properties file
            String env = pro.getProperty("env");

            if (env.equalsIgnoreCase("dev")) {
                url = pro.getProperty("baseurldev");
                username = pro.getProperty("usernamedev");
                password = pro.getProperty("passworddev");
            } else if (env.equalsIgnoreCase("uat")) {
                url = pro.getProperty("baseurluat");
                username = pro.getProperty("usernameuat");
                password = pro.getProperty("passworduat");
            } else if (env.equalsIgnoreCase("prod")) {
                url = pro.getProperty("baseurlprod");
                username = pro.getProperty("usernameprod");
                password = pro.getProperty("passwordprod");
            } else {
                throw new RuntimeException("Invalid environment in properties file: " + env);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }
    }

    public String geturl() {

        return url;
    }

    public String getusername() {

        return username;
    }

    public String getpassword() {

        return password;
    }
}
