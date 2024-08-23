package com.saucedemo.tests;

import com.saucedemo.driver.managment.Browser;
import com.saucedemo.driver.managment.DriverManager;
import com.saucedemo.models.User;
import com.saucedemo.pages.LoginButtonClickResult;
import com.saucedemo.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LoginTest {
    private static final String LINK = "https://www.saucedemo.com/";
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeEach
    public void setUp() {
        driver.set(DriverManager.getDriver(Browser.CHROME));
        getDriver().get(LINK);
        getDriver().manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver.get();
    }


    @ParameterizedTest
    @MethodSource("DataProviderForLogin")
    public void testLoginWithEmptyCredentials(User user) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterLoginFields(user);

        User emptyUser = new User();
        loginPage.enterLoginFields(emptyUser);

        LoginButtonClickResult loginButtonClickResult = loginPage.clickLoginButton();

        assertThat(loginButtonClickResult.getLoginPage(), notNullValue());
        assertThat(loginButtonClickResult.getLoginPage().getErrorMassageText(), equalTo("Username is required"));
    }


    @ParameterizedTest
    @MethodSource("DataProviderForLogin")
    public void testLoginWithEmptyPassword(User user) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterLoginFields(user);

        loginPage.enterPassword(null);

        LoginButtonClickResult loginButtonClickResult = loginPage.clickLoginButton();

        assertThat(loginButtonClickResult.getLoginPage(), notNullValue());
        assertThat(loginButtonClickResult.getLoginPage().getErrorMassageText(), equalTo("Password is required"));
    }


    @ParameterizedTest
    @MethodSource("DataProviderForLogin")
    public void testLoginWithCorrectInput(User user) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterLoginFields(user);

        LoginButtonClickResult loginButtonClickResult = loginPage.clickLoginButton();

        assertThat(loginButtonClickResult.getShopPage(), notNullValue());
        assertThat(loginButtonClickResult.getShopPage().getShopPageTitle(), equalTo("Swag Labs"));
    }


    @AfterEach
    public void setDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }


    static Stream<Arguments> DataProviderForLogin() {
        return Stream.of(
                Arguments.of(new User("standard_user", "secret_sauce")),
                Arguments.of(new User("problem_user", "secret_sauce")),
                Arguments.of(new User("performance_glitch_user", "secret_sauce")),
                Arguments.of(new User("error_user", "secret_sauce")),
                Arguments.of(new User("visual_user", "secret_sauce"))
        );
    }


}
