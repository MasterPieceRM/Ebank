package org.poo.cb;

import java.util.LinkedHashMap;
import java.util.Map;

public class User {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String address;
    private Portfolio portfolio;
    private Map<String, User> friends;
    private boolean hasPremium;

    private User(UserBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.address = builder.address;
        this.portfolio = new Portfolio();
        this.friends = new LinkedHashMap<>();
        this.hasPremium = false;
    }

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public Portfolio getPortfolio() {
        return portfolio;
    }
    public Map<String, User> getFriends() {
        return friends;
    }
    public boolean hasPremium() {
        return hasPremium;
    }
    public void setPremium() {
        this.hasPremium = true;
    }
    public void addFriend(User user) {
        friends.put(user.getEmail(), user);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"email\":\"").append(email).append("\",");
        sb.append("\"firstname\":\"").append(firstname).append("\",");
        sb.append("\"lastname\":\"").append(lastname).append("\",");
        sb.append("\"address\":\"").append(address).append("\",");
        sb.append("\"friends\":[");
        if (friends != null && !friends.isEmpty()) {
            for (String friendEmail : friends.keySet()) {
                sb.append("\"").append(friendEmail).append("\",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        return sb.toString();
    }

    public static class UserBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String address;
        private Portfolio portfolio;
        private Map<User, String> friends;

        public UserBuilder(String email) {
            this.email = email;
        }

        public UserBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
