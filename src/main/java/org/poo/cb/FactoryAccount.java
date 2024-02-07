package org.poo.cb;

import java.util.ArrayList;

public class FactoryAccount {
    private static FactoryAccount instance;
    private FactoryAccount() {}
    public static FactoryAccount getInstance() {
        if (instance == null) {
            instance = new FactoryAccount();
        }
        return instance;
    }
    public enum AccountType {
        EUR, USD, GBP, JPY, CAD
    }
    public Account createAccount(AccountType accType, ArrayList<Double> exchanges)
    {
        switch (accType) {
            case EUR:
                return new EUR(exchanges);
            case USD:
                return new USD(exchanges);
            case GBP:
                return new GBP(exchanges);
            case JPY:
                return new JPY(exchanges);
            case CAD:
                return new CAD(exchanges);
            default:
                throw new IllegalArgumentException("No such account type.");
        }
    }
}
