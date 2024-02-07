package org.poo.cb;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ebank {
    private static Ebank instance = null;
    private Map<String, User> users;
    private Map<FactoryAccount.AccountType, ArrayList<Double>> exchangeRates;
    private Map<String, ArrayList<Double>> stockMap;
    private ArrayList<String> recommendedStocks;

    private Ebank() {
        users = new LinkedHashMap<>();
        recommendedStocks = new ArrayList<>();
    }
    public static Ebank getInstance() {
        if (instance == null) {
            instance = new Ebank();
        }
        return instance;
    }
    public Map<String, User> getUsers() {
        return users;
    }

    public Map<FactoryAccount.AccountType, ArrayList<Double>> getExchangeRates() {
        return exchangeRates;
    }

    public Map<String, ArrayList<Double>> getStockMap() {
        return stockMap;
    }
    public ArrayList<String> getRecommendedStocks() {
        return recommendedStocks;
    }

    public void addUser(User user, String details) {
        users.put(details, user);
    }

    public void loadExchangeRates(String exchangeRatesFile) throws FileNotFoundException {
        Map<FactoryAccount.AccountType, ArrayList<Double>> exchangeRates = new EnumMap<>(FactoryAccount.AccountType.class);
        File myObj = new File(exchangeRatesFile);
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] values = data.split(",");
            FactoryAccount.AccountType baseCurrency = FactoryAccount.AccountType.valueOf(values[0]);
            ArrayList<Double> rates = new ArrayList<>();
            for (int j = 1; j < values.length; j++) {
                rates.add(Double.parseDouble(values[j]));
            }

            exchangeRates.put(baseCurrency, rates);
        }
        myReader.close();
        this.exchangeRates = exchangeRates;
    }
    public void processStockData(String stocksValueFile) throws FileNotFoundException {
        HashMap<String, ArrayList<Double>> stockMap = new HashMap<>();
        File myObj = new File(stocksValueFile);
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] values = data.split(",");
            String stockName = values[0];
            ArrayList<Double> prices = new ArrayList<>();
            for (int j = 1; j < values.length; j++) {
                prices.add(Double.parseDouble(values[j]));
            }
            stockMap.put(stockName, prices);
        }
        myReader.close();
        this.stockMap = stockMap;
    }
    public void stocksToBuy() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"stocksToBuy\":[");
        if (recommendedStocks != null && !recommendedStocks.isEmpty()) {
            for (String stockName : recommendedStocks) {
                sb.append("\"").append(stockName).append("\",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        System.out.println(sb);
    }
}

