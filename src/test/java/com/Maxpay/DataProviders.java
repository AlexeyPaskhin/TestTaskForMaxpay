package com.Maxpay;

import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "RightCredentials")
    public static Object[][] RightCredentials() {
        return new Object[][] {
                {"kkk@gmail.com", "1111111aA"}
        };
    }

    @DataProvider(name = "InvalidCredentials")
    public static Object[][] InvalidCredentials() {
        return new Object[][] {
                {"", ""},
                {"kkk@gmail.com", ""},
                {"", "1111111aA"},
                {"kkk@gmail.com", "1111111a"},
                {"kkk@gmail.co", "1111111aA"}

        };
    }

}
