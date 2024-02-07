package org.poo.cb;

import java.util.ArrayList;

public abstract class Account {
    protected ArrayList<Double> exchanges;
    protected double amount;

    public Account(ArrayList<Double> exchanges) {
        this.exchanges = exchanges;
        this.amount = 0.0;
    }
    public double getAmount() {
        return amount;
    }
    public void substractMoney(double amount) {
        this.amount -= amount;
    }
    public void addMoney(double amount) {
        this.amount += amount;
    }
    public abstract String displayAccountDetails();
    public void exchangeMoney(Account source, FactoryAccount.AccountType accountType,
                              Double amountToExchange, boolean isPremium) {
        switch (accountType) {
            case EUR:
                if (amountToExchange * exchanges.get(0) > source.getAmount()) {
                    System.out.println("Insufficient amount in account EUR for exchange");
                    return;
                }
                if (amountToExchange * exchanges.get(0) > source.getAmount() / 2 && !isPremium) {
                    source.substractMoney(amountToExchange * exchanges.get(0) * 0.01);
                }
                source.substractMoney(amountToExchange * exchanges.get(0));
                break;
            case USD:
                if (amountToExchange * exchanges.get(4) > source.getAmount()) {
                    System.out.println("Insufficient amount in account USD for exchange");
                    return;
                }
                if (amountToExchange * exchanges.get(4) > source.getAmount() / 2 && !isPremium) {
                    source.substractMoney(amountToExchange * exchanges.get(4) * 0.01);
                }
                source.substractMoney(amountToExchange * exchanges.get(4));
                break;
            case GBP:
                if (amountToExchange * exchanges.get(1) > source.getAmount()) {
                    System.out.println("Insufficient amount in account GBP for exchange");
                    return;
                }
                if (amountToExchange * exchanges.get(1) > source.getAmount() / 2 && !isPremium) {
                    source.substractMoney(amountToExchange * exchanges.get(1) * 0.01);
                }
                source.substractMoney(amountToExchange * exchanges.get(1));
                break;
            case JPY:
                if (amountToExchange * exchanges.get(2) > source.getAmount()) {
                    System.out.println("Insufficient amount in account JPY for exchange");
                    return;
                }
                if (amountToExchange * exchanges.get(2) > source.getAmount() / 2 && !isPremium) {
                    source.substractMoney(amountToExchange * exchanges.get(2) * 0.01);
                }
                source.substractMoney(amountToExchange * exchanges.get(2));
                break;
            case CAD:
                if (amountToExchange * exchanges.get(3) > source.getAmount()) {
                    System.out.println("Insufficient amount in account CAD for exchange");
                    return;
                }
                if (amountToExchange * exchanges.get(3) > source.getAmount() / 2 && !isPremium) {
                    source.substractMoney(amountToExchange * exchanges.get(3) * 0.01);
                }
                source.substractMoney(amountToExchange * exchanges.get(3));
                break;
            default:
                throw new IllegalArgumentException("No such account type.");
        }
        this.addMoney(amountToExchange);
    }
}

class EUR extends Account {
    public EUR(ArrayList<Double> exchanges) {
        super(exchanges);
    }
    @Override
    public String displayAccountDetails() {
        return "{\"currencyName\":\"EUR\",\"amount\":\"" + String.format("%.2f", this.amount) + "\"},";
    }
}

class USD extends Account {
    public USD(ArrayList<Double> exchanges) {
        super(exchanges);
    }
    @Override
    public String displayAccountDetails() {
        return "{\"currencyName\":\"USD\",\"amount\":\"" + String.format("%.2f", this.amount) + "\"},";
    }
}

class GBP extends Account {
    public GBP(ArrayList<Double> exchanges) {
        super(exchanges);
    }
    @Override
    public String displayAccountDetails() {
        return "{\"currencyName\":\"GBP\",\"amount\":\"" + String.format("%.2f", this.amount) + "\"},";
    }
}

class JPY extends Account {
    public JPY(ArrayList<Double> exchanges) {
        super(exchanges);
    }
    @Override
    public String displayAccountDetails() {
        return "{\"currencyName\":\"JPY\",\"amount\":\"" + String.format("%.2f", this.amount) + "\"},";
    }
}

class CAD extends Account {
    public CAD(ArrayList<Double> exchanges) {
        super(exchanges);
    }
    @Override
    public String displayAccountDetails() {
        return "{\"currencyName\":\"CAD\",\"amount\":\"" + String.format("%.2f", this.amount) + "\"},";
    }
}
