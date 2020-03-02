package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final List<SelenideElement> REPLENISH_BUTTONS = $$("[data-test-id=action-deposit]");

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.waitUntil(Condition.visible, 15000);
    }

    public FundsTransferPage replenishCardAccount(int index) {
        REPLENISH_BUTTONS.get(index).click();
        return new FundsTransferPage();
    }

    public int getBalance(String cardNumber) {
        String cardInfo = $(withText(cardNumber)).getText();
        String sumInfo = cardInfo.substring(cardInfo.indexOf("баланс:") + 7, cardInfo.lastIndexOf("р.")).trim();
        return Integer.parseInt(sumInfo);
    }
}
