package com.saucedemo.pages;

import com.saucedemo.models.User;
import com.saucedemo.tests.LoginTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    // TODO don't forget to format your code(CTRL+ALT+L on windows) and remove unused imports(CTRL+ALT+O on windows)
    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @FindBy(xpath = "//input[@data-test='username']")
    private WebElement usernameArea;

    @FindBy(xpath = "//input[@data-test='password']")
    private WebElement passwordArea;

    @FindBy(xpath = "//input[@id='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@data-test='error']")
    private List<WebElement> errorMassage; // TODO massage is great thing, but we need message :)

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public LoginPage enterLoginFields(User user) {
        logger.info("Entering login fields for user: {}", user); // TODO in logs u will have object reference, not the value of user

        enterUsername(user.getUsername());
        enterPassword(user.getPassword());
        return this;
    }


    public LoginPage enterUsername(String username) {
        // TODO remove commented-out code if it is not needed or make initialisation of WebDriverWait in the constructor
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(usernameArea));

        if(username != null) {
            usernameArea.sendKeys(username);
            logger.debug("Entered username: {}", username);
        } else {
            String password = passwordArea.getAttribute("value");;
            // TODO I'm not quite understand why do we need get password and refresh page if username is empty
            driver.navigate().refresh();
            usernameArea.clear();
            logger.debug("Cleared username field");
            if(!password.isEmpty()) {
                enterPassword(password);
            }
        }
        return this;
    }


    public LoginPage enterPassword(String password) {
        // TODO remove commented-out code if it is not needed or make initialisation of WebDriverWait in the constructor
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(passwordArea));

        if(password != null) {
            passwordArea.sendKeys(password);
            logger.debug("Entered password: {}", password);
        } else {
            String username = usernameArea.getAttribute("value");
            // TODO I'm not quite understand why do we need get username and refresh page if password is empty
            driver.navigate().refresh();
            passwordArea.clear();
            logger.debug("Cleared password field");
            if(!username.isEmpty()) {
                enterUsername(username);
            }
        }
        return this;
    }


    public LoginButtonClickResult clickLoginButton() {
        // TODO remove commented-out code if it is not needed or make initialisation of WebDriverWait in the constructor
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(loginButton));

        loginButton.click();
        logger.info("Clicked login button");

        if(driver.getCurrentUrl().equals("https://www.saucedemo.com/")) { // TODO to constants
            return new LoginButtonClickResult(this);
        } else {
            return new LoginButtonClickResult(new ShopPage(driver));
        }
    }

    // TODO is it necessary to make errorMessage as a List?
    // TODO in each test u can get only one error message and then handle it or assert
    // TODO it's not that locator and case as well where u collect a lot of errors,
    //                          so it is better to use String for errorMessage
    public String getErrorMassageText() {
        if(!errorMassage.isEmpty()) {
            String fullText = errorMassage.get(0).getText();
            return fullText.substring(14);
        } else {
            return "";
        }
    }


}
