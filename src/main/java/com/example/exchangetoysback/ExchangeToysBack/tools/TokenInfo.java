package com.example.exchangetoysback.ExchangeToysBack.tools;


import lombok.Data;

@Data
public class TokenInfo {

    private static String userName;
    private static TokenInfo singletonTokenInfo;
    private static String role;

    private TokenInfo() {

    }

    public static TokenInfo getInstance() {
        if (singletonTokenInfo == null)
            singletonTokenInfo = new TokenInfo();
        return singletonTokenInfo;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        TokenInfo.role = role;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        TokenInfo.userName = userName;
    }
}
