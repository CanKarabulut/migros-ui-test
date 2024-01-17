package com.migros.uitest.stepdefinitions;

import com.migros.uitest.utils.DriverManager;
import com.migros.uitest.utils.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;


public class step_definitions {

    private final WebDriver driver = DriverManager.getDriver();
    private final TestConfig testConfig = TestConfig.getInstance();
    @Given("^Open the Migros website$")
    public void ıAmOnTheMigrosWebsite() {
        driver.get(testConfig.getProperty("base.url"));
    }

    @Then("the Pet Shop page should open")
    public void thePetShopPageShouldOpen() throws InterruptedException {
        Thread.sleep(2000);
        WebElement petShopPageTitle = driver.findElement(By.id("product-filter-desktop-title"));
        String text = petShopPageTitle.getText();
        Assert.assertEquals(text,"Pet Shop");
    }

    @And("the products should be sorted by low price")
    public void theProductsShouldBeSortedByLowPrice() {
        WebElement sortButton = driver.findElement(By.id("mat-select-0"));
        sortButton.click();
        WebElement lowerPriceSorting = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div/mat-option[5]/span"));
        lowerPriceSorting.click();
    }

    @And("Close the popups")
    public void closeThePopups() throws InterruptedException {
        Thread.sleep(3000);
        WebElement acceptCookies = driver.findElement(By.id("accept-all"));
        acceptCookies.click();

        WebElement closeDeliveryAddresPopup = driver.findElement(By.xpath("/html/body/sm-root/div/sm-header/div/div[2]/div[1]/div/fa-icon"));
        closeDeliveryAddresPopup.click();
    }

    @When("I select Pet Shop from the Categories tab")
    public void ıSelectPetShopFromTheCategoriesTab() throws InterruptedException {
        WebElement categoriesButton = driver.findElement(By.className("categories-icon"));
        categoriesButton.click();
        Thread.sleep(3000);
        driver.get(testConfig.getProperty("migros.petshop.url"));
    }

    @And("Verifies that products are sorted from low price to high price")
    public void verifiesThatProductsAreSortedFromLowPriceToHighPrice() throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> productsPriceList = driver.findElements(By.id("new-amount"));
        List<Double> unSortedList = productsPriceList.stream().map(product -> Double.valueOf(product.getText().substring(0,4).replace(",","."))).collect(Collectors.toList());
        List<Double> sortedList = unSortedList.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(unSortedList, sortedList);
    }
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = "failure_screenshot_" + System.currentTimeMillis();
            testConfig.takeScreenshot(screenshotName);
        }

        testConfig.generateCucumberReport();

        DriverManager.getDriverThread().get().quit();
    }
}
