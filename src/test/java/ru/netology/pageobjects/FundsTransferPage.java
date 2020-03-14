package ru.netology.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.userdata.UserData;

import java.util.List;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class FundsTransferPage {
    private final SelenideElement AMOUNT_FIELD = $("[data-test-id=amount] input");
    private final SelenideElement FORM_CARD_FIELD = $("[data-test-id=from] input");
    private final SelenideElement TRANSFER_BUTTON = $("[data-test-id=action-transfer]");
    private final SelenideElement ERROR_NOTIFICATION = $(".notification_status_error");

    public FundsTransferPage() {
        SelenideElement HEADING = $(withText("Пополнение карты"));
        HEADING.waitUntil(Condition.visible, 15000);
    }

    public DashboardPage validTransferFunds(List<UserData.Card> cards, String amount, int senderIndex) {
        AMOUNT_FIELD.setValue(amount);
        FORM_CARD_FIELD.setValue(cards.get(senderIndex).getCardNumber());
        TRANSFER_BUTTON.click();
        return new DashboardPage();
    }

    public void invalidTransferFunds(List<UserData.Card> cards, String amount, int senderIndex) {
        AMOUNT_FIELD.setValue(amount);
        FORM_CARD_FIELD.setValue(cards.get(senderIndex).getCardNumber());
        TRANSFER_BUTTON.click();
    }
    public void assertErrorNotificationIsVisible() {
        ERROR_NOTIFICATION.shouldBe(Condition.visible);
    }
}
