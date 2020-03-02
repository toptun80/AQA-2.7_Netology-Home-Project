package ru.netology.pageobjects;

import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement LOGIN_FIELD = $("[name=login]");
    private final SelenideElement PASSWORD_FIELD = $("[name=password]");
    private final SelenideElement LOGIN_BUTTON = $("[data-test-id=action-login]");

    public VerificationPage validLogin(UserData.AuthInfo authInfo) {
        LOGIN_FIELD.setValue(authInfo.getLogin());
        PASSWORD_FIELD.setValue(authInfo.getPassword());
        LOGIN_BUTTON.click();
        return new VerificationPage();
    }
}
