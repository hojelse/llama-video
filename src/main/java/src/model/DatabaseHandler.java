package src.model;

import src.Exceptions.InvalidPasswordException;
import src.Exceptions.UserDoesNotExistException;
import src.Exceptions.UserNullException;
import src.Exceptions.UsernameTakenException;
import javafx.scene.control.Alert;

import java.io.*;

public class DatabaseHandler {

    public static String pathToDir;

    /**
     * Sets path of the SavedData folder right beside the dir of jar, such that
     * @param pathDir
     */
    public static void setPath(String pathDir){
        pathToDir = pathDir;
    }

    /**
     * if saveData folder does not exist then createSaveDataFolder() creates a saveData folder at pathToDir and creates a default 'admin' login
     */
    public static void createSaveDataFolder(){
        File file = new File(pathToDir);
        Boolean saveDataFolderExists = file.exists();
        if(!saveDataFolderExists){
            file.mkdirs();
            makeNewUserLogin("admin", "password");
            serializeUser(new User("admin", "password"));
        }
    }

    /**
     * Appends username and password to text file Users
     * @param username
     * @param password
     */
    public static void makeNewUserLogin(String username, String password){
        try {
            BufferedWriter output;
            output = new BufferedWriter(new FileWriter(pathToDir + "/Users", true));
            output.append(username + "; " + password);
            output.newLine();
            output.close();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("We have encountered an error while making your login");
            alert.show();
        }
    }

    /**
     * Serializing a user
     * @param user
     * @throws UsernameTakenException
     */
    public static void serializeUser(User user){

        try {
            if(user == null){
                throw new UserNullException();
            }
            File file = new File(pathToDir + "/" + user.getUsername());
            FileOutputStream FO = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(FO);
            out.writeObject(user);
            out.close();
            FO.close();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There was an error while savinng the user");
            alert.show();
        } catch (UserNullException e){
            e.printStackTrace();
        }

    }

    public static boolean userExists(String userName){
        File file = new File(pathToDir + "/" + userName);
        return file.exists();
    }

    public static void attemptLogin(String userName, String password) throws UserDoesNotExistException, InvalidPasswordException {

        if(!userExists(userName)){
            throw new UserDoesNotExistException(userName);
        } else {
            User user = readUser(userName);
            if(user.getPassword().equals(password)){
                StreamingService.getInstance().setCurrentUser(user);
            } else {
                throw new InvalidPasswordException();
            }
        }
    }



    public static User readUser(String username){
        try {
            FileInputStream file = new FileInputStream(pathToDir + "/" + username);
            ObjectInputStream in = new ObjectInputStream(file);
            User user = (User) in.readObject();
            return user;
        } catch (IOException | ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There was an error while reading from the saved data");
        }
        return null;
    }



}

