package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private List<SelenideElement> replenishButton = $$("[data-test-id=action-deposit]");

    public DashboardPage() {
        heading.waitUntil(Condition.visible, 15000);
    }

    public FundsTransferPage replenishCardAccount(int index) {
        replenishButton.get(index).click();
        return new FundsTransferPage();
    }
}
