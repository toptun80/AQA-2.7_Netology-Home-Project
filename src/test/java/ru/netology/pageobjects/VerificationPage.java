package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[name=code]");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.waitUntil(Condition.visible, 15000);
    }

    public DashboardPage validVerify(UserData.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}