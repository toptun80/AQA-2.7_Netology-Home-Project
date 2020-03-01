package ru.netology.userdata;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private UserData() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String cardBalance;
    }

    public static List<CardInfo> getCardInfo() {
        List<CardInfo> cards = new ArrayList<>();
        cards.add(new CardInfo("5559 0000 0000 0001", "10000"));
        cards.add(new CardInfo("5559 0000 0000 0002", "10000"));
        return cards;
    }
}
