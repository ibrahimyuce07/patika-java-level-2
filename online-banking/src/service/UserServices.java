package service;

import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import entity.User;

public class UserServices implements IUserServices {

    @Override
    public void register(String fullName, String tcNo, LocalDate birthDate, String password) throws Exception {

        User user = new User();
        if (password.contains(String.valueOf(birthDate.getYear()))) {
            throw new Exception("Password cannot contain birth year");
        } else {
            user.setFullName(fullName);
            user.setTcNo(tcNo);
            user.setBirthDate(birthDate);
            user.setPassword(password);
            user.setBalance(new BigDecimal(0));

            try {
                String userJson = toJson(user);
                FileWriter fileWriter = new FileWriter("data/users.json", true);
                fileWriter.write(userJson);
                fileWriter.close();
            } catch (Exception e) {
                throw new Exception("Error while writing to file");
            }

        }

        System.out.println("User registered successfully");

    }

    private String toJson(User user) {
        return "{" + "\"fullName\":\"" + user.getFullName() + "\",\"tcNo\":\"" + user.getTcNo() + "\",\"birthDate\":\""
                + user.getBirthDate() + "\",\"password\":\"" + user.getPassword() + "\",\"balance\":\""
                + user.getBalance()
                + "\"}," + "\n";
    }

    @Override
    public void login(String tcNo, String password) throws Exception {

        User user = new User();
        try {
            FileReader fileReader = new FileReader("data/users.json");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(tcNo)) {
                    user = fromJson(line);
                    if (user.getPassword().equals(password)) {
                        System.out.println("Login successful");
                    } else {
                        throw new Exception("Wrong password");
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            throw new Exception("User not found");
        }

    }

    private User fromJson(String line) {
        User user = new User();
        String[] parts = line.split(",");
        user.setFullName(parts[0].split(":")[1].replace("\"", ""));
        user.setTcNo(parts[1].split(":")[1].replace("\"", ""));
        user.setBirthDate(LocalDate.parse(parts[2].split(":")[1].replace("\"", "")));
        user.setPassword(parts[3].split(":")[1].replace("\"", ""));
        user.setBalance(new BigDecimal(parts[4].split(":")[1].replace("\"", "")));
        return user;
    }

    @Override
    public void draw(BigDecimal amount) throws Exception {
        User user = new User();
        try {
            FileReader fileReader = new FileReader("data/users.json");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("12345678901")) {
                    user = fromJson(line);
                    if (user.getBalance().compareTo(amount) == 1) {
                        user.setBalance(user.getBalance().subtract(amount));
                        System.out.println("Draw successful");
                    } else {
                        throw new Exception("Not enough balance");
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            throw new Exception("User not found");
        }

    }

    @Override
    public void deposit(BigDecimal amount) throws Exception {
        User user = new User();
        try {
            FileReader fileReader = new FileReader("data/users.json");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("12345678901")) {
                    user = fromJson(line);
                    user.setBalance(user.getBalance().add(amount));
                    System.out.println("Deposit successful");
                }
                scanner.close();
            }
        } catch (Exception e) {
            throw new Exception("User not found");
        }

    }

    @Override
    public void transfer(BigDecimal amount, int userTcNo) throws Exception {
        User sender = new User();
        User receiver = new User();

        try {
            FileReader fileReader = new FileReader("data/users.json");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("12345678901")) {
                    sender = fromJson(line);
                    if (sender.getBalance().compareTo(amount) == 1) {
                        sender.setBalance(sender.getBalance().subtract(amount));
                        System.out.println("Transfer successful");
                    } else {
                        throw new Exception("Not enough balance");
                    }
                }
                scanner.close();
            }

        } catch (Exception e) {
            throw new Exception("User not found");
        }

        try {
            FileReader fileReader = new FileReader("data/users.json");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(String.valueOf(userTcNo))) {
                    receiver = fromJson(line);
                    receiver.setBalance(receiver.getBalance().add(amount));
                    System.out.println("Transfer successful");
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new Exception("User not found");
        }

    }

}
