package com.migros.uitest.stepdefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.migros.uitest.stepdefinitions"},
        plugin = {"pretty", "json:target/cucumber-html-reports/cucumber.json"}
)
public class CucumberRunner {
}
