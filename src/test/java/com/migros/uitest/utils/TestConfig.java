package com.migros.uitest.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestConfig {

    private static final String CONFIG_FILE = "config.properties";

    public static final String SCREENSHOT_PATH = "screenshots/";
    public static final String SCREENSHOT_FORMAT = ".png";
    private static TestConfig instance;
    private final Properties properties;

    private TestConfig() {
        properties = new Properties();
        loadConfig();
    }

    public static TestConfig getInstance() {
        if (instance == null) {
            instance = new TestConfig();
        }
        return instance;
    }

    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println(CONFIG_FILE + " file not found.");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void takeScreenshot(String screenshotName) {
        WebDriver driver = DriverManager.getDriverThread().get();
        if (driver != null) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File screenshot = ts.getScreenshotAs(OutputType.FILE);
                String screenshotPath = SCREENSHOT_PATH + screenshotName + SCREENSHOT_FORMAT;
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                System.out.println("Screenshot Captured " + screenshotPath);
            } catch (Exception e) {
                System.out.println("Screenshot not Captured: " + e.getMessage());
            }
        }
    }

    public void generateCucumberReport() {
        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-html-reports/cucumber.json");

        String projectName = "Migros Price Sorting Test";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
