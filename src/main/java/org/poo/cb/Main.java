package org.poo.cb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
        } else {
            if (args.length != 3) {
                System.out.println("Not enough args");
                System.exit(0);
            }
            Ebank ebank = Ebank.getInstance();
            ebank.getUsers().clear();
            ebank.getRecommendedStocks().clear();
            String resourcesPath = "src\\main\\resources\\";
            String exchangeRatesFile = args[0];
            String stocksValueFile = args[1];
            String commandsFile = args[2];
            try {
                ebank.loadExchangeRates(resourcesPath + exchangeRatesFile);
                ebank.processStockData(resourcesPath + stocksValueFile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            CommandInvoker invoker = new CommandInvoker();
            try {
                File myObj = new File(resourcesPath + commandsFile);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    invoker.invoke(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}