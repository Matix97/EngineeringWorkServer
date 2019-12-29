package com.example.exchangetoysback.ExchangeToysBack.tools;


import lombok.Data;

@Data
public class TokenInfo {

    private static String userName;
    private static TokenInfo singletonTokenInfo;

    private TokenInfo() {

    }

    public static TokenInfo getInstance() {
        if (singletonTokenInfo == null)
            singletonTokenInfo = new TokenInfo();
        return singletonTokenInfo;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        TokenInfo.userName = userName;
    }
}
