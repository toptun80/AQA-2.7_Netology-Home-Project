package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
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
    @CsvFileSource(resources = "/cardsIndexes.csv", numLinesToSkip = 1)
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
}