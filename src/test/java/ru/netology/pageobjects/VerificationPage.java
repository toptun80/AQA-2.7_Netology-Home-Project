package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement CODE_FIELD = $("[name=code]");
    private final SelenideElement VERIFY_BUTTON = $("[data-test-id=action-verify]");

    public VerificationPage() {
        CODE_FIELD.waitUntil(Condition.visible, 15000);
    }

    public DashboardPage validVerify(UserData.VerificationCode verificationCode) {
        CODE_FIELD.setValue(verificationCode.getAuthCode());
        VERIFY_BUTTON.click();
        return new DashboardPage();
    }
}