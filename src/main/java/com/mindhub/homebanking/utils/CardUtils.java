package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils() {

    }

    public static int getCVV() {
        int cvvNumber = ((int) ((Math.random() * (999 - 100)) + 0));
        return cvvNumber;
    }

    public static String getCardNumber() {
        String cardNumber = ((int) ((Math.random() * (9999 - 1000)) + 0)) + "-" + ((int) ((Math.random() * (9999 - 1000)) + 0)) + "-" +
                ((int) ((Math.random() * (9999 - 1000)) + 0)) + "-" + ((int) ((Math.random() * (9999 - 1000)) + 0));
        return cardNumber;
    }

}
