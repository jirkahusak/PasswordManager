package me.jirkahusak.auth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {
    public FileHandling() {
        File folder = new File(System.getProperty("user.dir"), "Auth");
        File auth = new File(folder, "auth.json");
        File passwords = new File(folder, "passwords.json");

        if (!folder.exists()) {
            boolean created = folder.mkdir(); // Create the folder
            if (created) {
                System.out.println("Folder created successfully.");
            }
        }


        try {
            if(auth.createNewFile()){
                fileFormat();
                System.out.println("File created successfully.");
            } else{
                if(auth.length() == 0){
                    fileFormat();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a file.");
        }

        try {
            if(passwords.createNewFile()){
                System.out.println("File created successfully.");
            }

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a file.");
        }
    }

    private static void fileFormat(){
        try {
            FileWriter fileWriterOne = new FileWriter(System.getProperty("user.dir") + "/Auth/passwords.json");
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/Auth/auth.json");
            fileWriter.write("[]");
            fileWriterOne.write("[]");
            fileWriter.close();
            fileWriterOne.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

