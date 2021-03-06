package ru.netology.test;

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

public class FundsTransferTest {
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
    @DisplayName("Перевод с карты на карту половины остатка счета")
    @CsvFileSource(resources = "/validTransferParams.csv", numLinesToSkip = 1)
    void shouldTransferFundsBetweenCards(int recipientIndex,
                                         int senderIndex,
                                         String recipientCardNumber,
                                         String senderCardNumber) {
        val recipientCardBalanceBeforeTransfer = dashboardPage.getBalance(recipientCardNumber);
        val senderCardBalanceBeforeTransfer = dashboardPage.getBalance(senderCardNumber);
        val fundsTransferPage = dashboardPage.replenishCardAccount(recipientIndex);
        val cards = UserData.getCardInfo();
        String amount = String.valueOf(senderCardBalanceBeforeTransfer / 2);
        dashboardPage = fundsTransferPage.validTransferFunds(cards, amount, senderIndex);
        val recipientCardBalanceAfterTransfer = dashboardPage.getBalance(recipientCardNumber);
        val senderCardBalanceAfterTransfer = dashboardPage.getBalance(senderCardNumber);
        val diffRecipientCard = recipientCardBalanceBeforeTransfer + Integer.parseInt(amount);
        val diffSenderCard = senderCardBalanceBeforeTransfer - Integer.parseInt(amount);
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
        fundsTransferPage.invalidTransferFunds(cards, amount, senderIndex);
        fundsTransferPage.assertErrorNotificationIsVisible();
    }
}