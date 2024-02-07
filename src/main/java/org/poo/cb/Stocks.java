package org.poo.cb;

import java.util.ArrayList;

public class Stocks {
    private final String name;
    private ArrayList<Double> lastPrices;
    private int amount;
    public Stocks(String name, ArrayList<Double> lastPrices, int amount) {
        this.name = name;
        this.lastPrices = lastPrices;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Double> getLastPrices() {
        return lastPrices;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public boolean isRecommended(ArrayList<String> recommendedStocks) {
        return recommendedStocks.contains(name);
    }
}
