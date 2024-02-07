package org.poo.cb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class CommandInvoker {

    public void invoke(String commandLine) {
        String[] parts = commandLine.split(" ");
        Command command;
        if (parts[0] == null || parts[0].isEmpty()) {
            return;
        }
        switch (parts[0]) {
            case "CREATE":
                command = new CreateUserCommand(parts);
                break;
            case "LIST":
                if (parts[1].equals("USER"))
                    command = new ListUserCommand(parts);
                else if(parts[1].equals("PORTFOLIO"))
                    command = new ListPortfolioCommand(parts);
                else
                    throw new IllegalArgumentException("Unknown command: " + parts[0]);
                break;
            case "ADD":
                switch (parts[1]) {
                    case "FRIEND":
                        command = new AddFriendCommand(parts);
                        break;
                    case "ACCOUNT":
                        command = new AddAccountCommand(parts);
                        break;
                    case "MONEY":
                        command = new AddMoneyCommand(parts);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown command: " + parts[0]);
                }
                break;
            case "EXCHANGE":
                command = new ExchangeCommand(parts);
                break;
            case "TRANSFER":
                command = new TransferCommand(parts);
                break;
            case "BUY":
                if (parts[1].equals("STOCKS"))
                    command = new BuyCommand(parts);
                else if (parts[1].equals("PREMIUM"))
                    command = new BuyPremiumCommand(parts);
                else
                    throw new IllegalArgumentException("Unknown command: " + parts[0]);
                break;
            case "RECOMMEND":
                command = new RecommendCommand();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + parts[0]);
        }
        command.execute();
    }
}
class CreateUserCommand implements Command {
    private final String[] userDetails;

    public CreateUserCommand(String[] userDetails) {
        this.userDetails = userDetails;
    }
    @Override
    public void execute() {
        String[] addressParts = Arrays.copyOfRange(userDetails, 5, userDetails.length);
        String address = String.join(" ", addressParts);
        if (Ebank.getInstance().getUsers().get(userDetails[2]) != null) {
            System.out.println("User with " + userDetails[2] + " already exists");
            return;
        }
        User user = new User.UserBuilder(userDetails[2])
                .firstname(userDetails[3])
                .lastname(userDetails[4])
                .address(address)
                .build();
        Ebank.getInstance().addUser(user, userDetails[2]);
    }
}

class ListUserCommand implements Command {
    private final String[] details;

    public ListUserCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        if (Ebank.getInstance().getUsers().get(details[2]) == null) {
            System.out.println("User with " + details[2] + " doesn't exist");
            return;
        }
        User user = Ebank.getInstance().getUsers().get(details[2]);
        System.out.println(user);
    }
}
class AddFriendCommand implements Command {
    private final String[] details;

    public AddFriendCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        User user1 = Ebank.getInstance().getUsers().get(details[2]);
        User user2 = Ebank.getInstance().getUsers().get(details[3]);
        if (user1 == null) {
            System.out.println("User with " + details[2] + " doesn't exist");
            return;
        }
        if (user2 == null) {
            System.out.println("User with " + details[3] + " doesn't exist");
            return;
        }
        if (user1.getFriends().get(details[3]) != null) {
            System.out.println("User with " + details[3] + " is already a friend");
            return;
        }
        user1.addFriend(user2);
        user2.addFriend(user1);
    }
}
class AddAccountCommand implements Command {
    private final String[] details;

    public AddAccountCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        ArrayList<Double> rates = ebank.getExchangeRates().get(FactoryAccount.AccountType.valueOf(details[3]));
        FactoryAccount factory = FactoryAccount.getInstance();
        Account newAccount = null;
        switch (details[3]) {
            case "EUR":
                if (user.getPortfolio().getAccount(FactoryAccount.AccountType.EUR) != null) {
                    System.out.println("Account in currency " + details[3] + " already exists for user");
                    return;
                }
                newAccount = factory.createAccount(FactoryAccount.AccountType.EUR, rates);
                user.getPortfolio().addAccount(newAccount, FactoryAccount.AccountType.EUR);
                break;
            case "USD":
                if (user.getPortfolio().getAccount(FactoryAccount.AccountType.USD) != null) {
                    System.out.println("Account in currency " + details[3] + " already exists for user");
                    return;
                }
                newAccount = factory.createAccount(FactoryAccount.AccountType.USD, rates);
                user.getPortfolio().addAccount(newAccount, FactoryAccount.AccountType.USD);
                break;
            case "GBP":
                if (user.getPortfolio().getAccount(FactoryAccount.AccountType.GBP) != null) {
                    System.out.println("Account in currency " + details[3] + " already exists for user");
                    return;
                }
                newAccount = factory.createAccount(FactoryAccount.AccountType.GBP, rates);
                user.getPortfolio().addAccount(newAccount, FactoryAccount.AccountType.GBP);
                break;
            case "JPY":
                if (user.getPortfolio().getAccount(FactoryAccount.AccountType.JPY) != null) {
                    System.out.println("Account in currency " + details[3] + " already exists for user");
                    return;
                }
                newAccount = factory.createAccount(FactoryAccount.AccountType.JPY, rates);
                user.getPortfolio().addAccount(newAccount, FactoryAccount.AccountType.JPY);
                break;
            case "CAD":
                if (user.getPortfolio().getAccount(FactoryAccount.AccountType.CAD) != null) {
                    System.out.println("Account in currency " + details[3] + " already exists for user");
                    return;
                }
                newAccount = factory.createAccount(FactoryAccount.AccountType.CAD, rates);
                user.getPortfolio().addAccount(newAccount, FactoryAccount.AccountType.CAD);
                break;
        }
    }
}
class AddMoneyCommand implements Command {
    private final String[] details;

    public AddMoneyCommand(String[] details) {
        this.details = details;
    }
    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        Account account = user.getPortfolio().getAccount(FactoryAccount.AccountType.valueOf(details[3]));
        account.addMoney(Double.parseDouble(details[4]));
    }
}
class ExchangeCommand implements Command {
    private final String[] details;

    public ExchangeCommand(String[] details) {
        this.details = details;
    }
    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        Account source = user.getPortfolio().getAccount(FactoryAccount.AccountType.valueOf(details[3]));
        Account destination = user.getPortfolio().getAccount(FactoryAccount.AccountType.valueOf(details[4]));
        Double amount = Double.parseDouble(details[5]);
        destination.exchangeMoney(source, FactoryAccount.AccountType.valueOf(details[3]), amount, user.hasPremium());
    }
}
class ListPortfolioCommand implements Command {
    private final String[] details;

    public ListPortfolioCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        if (Ebank.getInstance().getUsers().get(details[2]) == null) {
            System.out.println("User with " + details[2] + " doesn't exist");
            return;
        }
        User user = Ebank.getInstance().getUsers().get(details[2]);
        System.out.println(user.getPortfolio());
    }
}
class TransferCommand implements Command {
    private final String[] details;

    public TransferCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        User friend = ebank.getUsers().get(details[3]);
        Double amount = Double.parseDouble(details[5]);
        Account source = user.getPortfolio().getAccount(FactoryAccount.AccountType.valueOf(details[4]));
        Account destination = friend.getPortfolio().getAccount(FactoryAccount.AccountType.valueOf(details[4]));
        if (user.getFriends().get(details[3]) == null) {
            System.out.println("You are not allowed to transfer money to " + details[3]);
            return;
        }
        if (source.getAmount() < amount) {
            System.out.println("Insufficient amount in account " + details[4] + " for transfer");
            return;
        }
        source.substractMoney(amount);
        destination.addMoney(amount);
    }
}
class BuyCommand implements Command {
    private final String[] details;

    public BuyCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        int amount = Integer.parseInt(details[4]);
        Account buyer = user.getPortfolio().getAccount(FactoryAccount.AccountType.USD);
        if (buyer.getAmount() < amount * ebank.getStockMap().get(details[3]).get(9)) {
            System.out.println("Insufficient amount in account for buying stock");
            return;
        }
        user.getPortfolio().buyStock(user, details[3], amount);
    }
}
class RecommendCommand implements Command {
    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        Map<String, ArrayList<Double>> stockMap = ebank.getStockMap();
        for (String stockName : stockMap.keySet()) {
            Double longTermAverage = 0.0;
            Double shortTermAverage = 0.0;
            ArrayList<Double> prices = stockMap.get(stockName);
            for (int i = 0; i < 10; i++) {
                if (i > 4) {
                    shortTermAverage += prices.get(i);
                }
                longTermAverage += prices.get(i);
            }
            if (shortTermAverage / 5 > longTermAverage / 10) {
                ebank.getRecommendedStocks().add(stockName);
            }
        }
        ebank.stocksToBuy();
    }
}
class BuyPremiumCommand implements Command {
    private final String[] details;

    public BuyPremiumCommand(String[] details) {
        this.details = details;
    }

    @Override
    public void execute() {
        Ebank ebank = Ebank.getInstance();
        User user = ebank.getUsers().get(details[2]);
        if (user == null) {
            System.out.println("User with " + details[2] + " doesn't exist");
            return;
        }
        Account buyer = user.getPortfolio().getAccount(FactoryAccount.AccountType.USD);
        if (buyer.getAmount() < 100) {
            System.out.println("Insufficient amount in account for buying premium option");
            return;
        }
        user.setPremium();
        buyer.substractMoney(100);
    }
}
