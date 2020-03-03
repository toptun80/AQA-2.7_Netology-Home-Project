package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.pageobjects.DashboardPage;
import ru.netology.pageobjects.LoginPage;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class fundsTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void openDashBoard() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = UserData.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = UserData.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @ParameterizedTest
    @DisplayName("Перевод с карты на карту в пределах остатка счета")
    @CsvFileSource(resources = "/validTransferParams.csv", numLinesToSkip = 1)
    void shouldTransferFundsBetweenCards(int recipientIndex,
                                         int senderIndex,
                                         String amount,
                                         String recipientCardNumber,
                                         String senderCardNumber) {
        int recipientCardBalanceBeforeTransfer = dashboardPage.getBalance(recipientCardNumber);
        int senderCardBalanceBeforeTransfer = dashboardPage.getBalance(senderCardNumber);
        val fundsTransferPage = dashboardPage.replenishCardAccount(recipientIndex);
        val cards = UserData.getCardInfo();
        dashboardPage = fundsTransferPage.validTransferFunds(cards, amount, senderIndex);
        int recipientCardBalanceAfterTransfer = dashboardPage.getBalance(recipientCardNumber);
        int senderCardBalanceAfterTransfer = dashboardPage.getBalance(senderCardNumber);
        int diffRecipientCard = recipientCardBalanceBeforeTransfer + Integer.parseInt(amount);
        int diffSenderCard = senderCardBalanceBeforeTransfer - Integer.parseInt(amount);
        assertEquals(recipientCardBalanceAfterTransfer, diffRecipientCard);
        assertEquals(senderCardBalanceAfterTransfer, diffSenderCard);
    }

    @ParameterizedTest
    @DisplayName("Перевод суммы превышающей остаток счета")
    @CsvFileSource(resources = "/invalidTransferParams.csv", numLinesToSkip = 1)
    void shouldNotifyTransferAmountExceedsAvailableFunds(int recipientIndex, int senderIndex, String senderCardNumber) {
        int senderCardBalanceBeforeTransfer = dashboardPage.getBalance(senderCardNumber);
        val fundsTransferPage = dashboardPage.replenishCardAccount(recipientIndex);
        val cards = UserData.getCardInfo();
        String amount = String.valueOf(senderCardBalanceBeforeTransfer * 2);
        SelenideElement ERROR_NOTIFICATION = fundsTransferPage.invalidTransferFunds(cards, amount, senderIndex);
        ERROR_NOTIFICATION.shouldBe(Condition.visible);
    }
}