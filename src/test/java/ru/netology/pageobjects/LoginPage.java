package ru.netology.pageobjects;

import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[name=login]");
    private SelenideElement passwordField = $("[name=password]");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

        public VerificationPage validLogin(UserData.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}
