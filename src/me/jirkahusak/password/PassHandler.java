package me.jirkahusak.password;

import me.jirkahusak.crypt.Encryption;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PassHandler {
    private static final String filePath = System.getProperty("user.dir") + "/Auth/passwords.json";

    public static JSONArray usersArray = readExistingUsers();

    public static void createLogin(String username,String password, String keyPassword, int id, String website){
        JSONObject newLogin = createNewLogin(username, password, keyPassword, id, website);
        usersArray.put(newLogin);

        writeJSONToFile(usersArray);

    }

    private static JSONArray readExistingUsers() {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONArray(fileContent);
        } catch (IOException e) {
            return new JSONArray();
        }
    }

    private static JSONObject createNewLogin(String username, String password, String keyPassword, int id, String website) {
        JSONObject user = new JSONObject();
        try {
            String EncryptPass = Encryption.Encrypt(password, keyPassword);
            String EncryptUser = Encryption.Encrypt(username, keyPassword);

            user.put("id", id);
            user.put("website", website);
            user.put("username", EncryptUser);
            user.put("password", EncryptPass);
            return user;
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private static void writeJSONToFile(JSONArray users){
        try{
            FileWriter filewriter = new FileWriter(filePath);
            filewriter.write(users.toString(4));
            filewriter.flush();
            filewriter.close();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Couldn't write data.");
        }
    }

    public static void printAllLogin(int id, String password){
        for(int i = 0; i < usersArray.length(); i++){
            JSONObject jsonObject = usersArray.getJSONObject(i);
            if(id == jsonObject.getInt("id")){
                try{
                    String website = jsonObject.getString("website");
                    String user = Encryption.Decrypt(jsonObject.getString("username"), password);
                    String pass = Encryption.Decrypt(jsonObject.getString("password"), password);

                    System.out.println();
                    System.out.println("Username: " + user);
                    System.out.println("Password: " + pass);
                    System.out.println("Website: " + website);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
