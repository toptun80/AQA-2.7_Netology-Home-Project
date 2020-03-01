package ru.netology.test;

import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.pageobjects.LoginPage;
import ru.netology.userdata.UserData;

import static com.codeborne.selenide.Selenide.open;

public class fundsTransferTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/cardsIndexes.csv", numLinesToSkip = 1)
    void shouldTransferFundsBetweenCards(int recipientIndex, int senderIndex, String amount) {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = UserData.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = UserData.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val fundsTransferPage = dashboardPage.replenishCardAccount(recipientIndex);
        val cards = UserData.getCardInfo();
        fundsTransferPage.executeTransferFunds(cards, amount, recipientIndex, senderIndex);
    }
}