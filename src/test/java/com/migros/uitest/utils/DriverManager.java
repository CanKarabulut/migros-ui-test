package com.migros.uitest.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            initializeDriver();
        }
        return driverThread.get();
    }

    private static void initializeDriver() {
        String browser = TestConfig.getInstance().getProperty("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driverThread.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driverThread.set(new FirefoxDriver());
                break;
            default:
                throw new IllegalArgumentException("Invalid Browser " + browser);
        }

        maximizeWindow();
    }

    private static void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

   public static ThreadLocal<WebDriver> getDriverThread() {
        return driverThread;
   }
}
