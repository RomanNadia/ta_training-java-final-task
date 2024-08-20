package com.saucedemo.pages;

public class LoginButtonClickResult {
    private LoginPage loginPage;
    private ShopPage shopPage;

    public LoginButtonClickResult(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public LoginButtonClickResult(ShopPage shopPage) {
        this.shopPage = shopPage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public ShopPage getShopPage() {
        return shopPage;
    }
}
