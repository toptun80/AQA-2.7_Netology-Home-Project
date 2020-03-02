package ru.netology.userdata;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private UserData() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String authCode;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        String cardNumber;
    }

    public static List<Card> getCardInfo() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("5559 0000 0000 0001"));
        cards.add(new Card("5559 0000 0000 0002"));
        return cards;
    }
}
