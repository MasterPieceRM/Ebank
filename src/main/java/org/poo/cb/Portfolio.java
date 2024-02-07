package org.poo.cb;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class Portfolio {
    private Map<FactoryAccount.AccountType, Account> accounts;
    private Map<String, Stocks> stocks;
    public Portfolio() {
        accounts = new LinkedHashMap<>();
        stocks = new LinkedHashMap<>();

    }

    public void addAccount(Account account, FactoryAccount.AccountType AccountType ) {
        accounts.put(AccountType, account);
    }
    public void buyStock(User buyer, String stockName, int amount) {
        if (stocks.containsKey(stockName)) {
            stocks.get(stockName).setAmount(stocks.get(stockName).getAmount() + amount);
        } else {
            Ebank ebank = Ebank.getInstance();
            Stocks stock = new Stocks(stockName, ebank.getStockMap().get(stockName), amount);
            stocks.put(stockName, stock);
        }
        Double finalPrice = amount * stocks.get(stockName).getLastPrices().get(9);
        Ebank ebank = Ebank.getInstance();
        if (buyer.hasPremium() && stocks.get(stockName).isRecommended(ebank.getRecommendedStocks())) {
            finalPrice -= finalPrice * 0.05;
        }
        buyer.getPortfolio().getAccount(FactoryAccount.AccountType.USD)
                .substractMoney(finalPrice);
    }
    public Account getAccount(FactoryAccount.AccountType accountType) {
        return accounts.get(accountType);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"stocks\":[");
        if (stocks != null && !stocks.isEmpty()) {
            for (String stockName : stocks.keySet()) {
                sb.append("{\"stockName\":\"").append(stockName).append("\",");
                sb.append("\"amount\":").append(stocks.get(stockName).getAmount()).append("},");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("],");
        sb.append("\"accounts\":[");
        if (accounts != null && !accounts.isEmpty()) {
            for (FactoryAccount.AccountType accountType : accounts.keySet()) {
                sb.append(accounts.get(accountType).displayAccountDetails());
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");

        return sb.toString();
    }
}