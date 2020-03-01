package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import java.util.List;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class FundsTransferPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public FundsTransferPage() {
        heading.waitUntil(Condition.visible, 15000);
    }

    public DashboardPage executeTransferFunds(List<UserData.CardInfo> cards,
                                              String amount,
                                              int recipientIndex,
                                              int senderIndex) {
        amountField.setValue(amount);
        fromCardField.setValue(cards.get(senderIndex).getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
