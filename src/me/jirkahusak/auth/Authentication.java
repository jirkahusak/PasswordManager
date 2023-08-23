package me.jirkahusak.auth;

import me.jirkahusak.crypt.Hash;
import me.jirkahusak.password.PassHandler;
import org.json.JSONObject;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Authentication extends AccountHandler{
    Scanner scan = new Scanner(System.in);

    public void Register(){
        String secondPassword;
        String password;
        do {
            System.out.print("Enter Username: ");
            String username = scan.nextLine();
            checkAccount(username);
            if (accountExists) {
                System.out.println("Username already exists!");
            }else {
                System.out.print("Enter password: ");
                password = scan.nextLine();
                System.out.print("Enter password again: ");
                secondPassword = scan.nextLine();


                if (password.equals(secondPassword)) {
                    createAccount(username, password);
                    for(int i = 0; i < usersArray.length(); i++) {
                        JSONObject jsonObject = usersArray.getJSONObject(i);
                        String hashUser = hashSHA512(username);
                        String user = jsonObject.getString("username");
                        accountId = jsonObject.getInt("id");
                        if(user.equals(hashUser)){
                            break;
                        }
                    }
                    Login();
                    break;
                } else {
                    System.out.println("Passwords don't match!");
                }
            }
        } while(true);
    }

    public void Login(){
        do{
            System.out.print("Enter Username: ");
            String username = scan.nextLine();
            System.out.print("Enter password: ");
            String password = scan.nextLine();

            checkAccount(username);

                if (accountExists) {
                    checkPassword(password);
                    if (rightPassword) {
                        System.out.println("Logged in successfully!");
                        do {
                            System.out.println("What do you want to do?");
                            try {
                                System.out.print("(1) Create new login. (2) Read all your logins: ");
                                int loginDecision = scan.nextInt();
                                scan.nextLine();
                                if(loginDecision == 1){
                                    System.out.print("Username: ");
                                    String user = scan.nextLine();
                                    System.out.print("Password: ");
                                    String pass = scan.nextLine();
                                    String key = Hash.hashBlake2B(password);
                                    System.out.print("Website: ");
                                    String web = scan.nextLine();
                                    PassHandler.createLogin(user,pass,key,id,web);
                                }else if(loginDecision == 2){
                                    String key = Hash.hashBlake2B(password);
                                    PassHandler.printAllLogin(id, key);
                                }else{
                                    System.out.println("Choose valid option!");
                                }
                            }catch (InputMismatchException e){
                                System.out.println("Choose valid option!");
                            }
                        }while(true);
                    } else {
                        System.out.println("Wrong password. Try again");
                    }
                } else {
                    System.out.print("Username doesn't exist. Do you want to create one? Y/N: ");
                    String decisionCreate = scan.nextLine();
                    if (decisionCreate.equalsIgnoreCase("Y") || decisionCreate.equalsIgnoreCase("Yes")) {
                        Register();
                        break;
                    } else if (!decisionCreate.equalsIgnoreCase("N") && !decisionCreate.equalsIgnoreCase("No")) {
                        System.out.println("Choose a valid option.");
                    }
                }
        }while(true);
    }
    public Authentication() {
        do {
            File file = new File(System.getProperty("user.dir") + "/Auth/auth.json");
            if(file.length() == 2){
                Register();
                break;
            }
            try {
                System.out.print("Do you want Login(1) or Register(2): ");
                int decision = scan.nextInt();
                scan.nextLine();
                if (decision == 1) {
                    Login();
                    break;
                } else if (decision == 2) {
                    Register();
                    break;
                } else {
                    System.out.println("Choose valid option.");
                }
            }catch (InputMismatchException e){
                System.out.println("Choose valid option.");
                scan.nextLine();
            }
        } while (true);
    }
}
