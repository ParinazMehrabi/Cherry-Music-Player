package org.example.graphic.controller;

import org.example.graphic.exceptions.InvalidFormatException;
import org.example.graphic.exceptions.NotAvailableId;
import org.example.graphic.exceptions.UserNotFoundException;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.interfaces.GeneralOperations;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.Listener;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.graphic.controller.ArtistController.getArtistController;
import static org.example.graphic.controller.ListenerController.getListenerController;
import static org.example.graphic.controller.UserController.*;
import static org.example.graphic.model.DataBase.getDataBase;

public class SignupController implements GeneralOperations
{
    private UserAccount account;
    private static SignupController signupController;
    public static SignupController getSignupController() {
        if (signupController == null)
            signupController = new SignupController();
        return signupController;
    }
    public void setAccount(UserAccount account)
    {
        this.account = account;
    }

    public UserAccount getAccount() {
        return account;
    }
    @Override
    public String signup(char type, String userName, String password, String fullName, String email, String phoneNumber, String birthDate, String biography) throws ParseException, InvalidFormatException, ParseException, UserNotFoundException {
        UserAccount userAccount = null;
        boolean f = false;
        if (!checkNumberValidation(phoneNumber)) {
            throw new InvalidFormatException("phone number is invalid");
        }
        if (!checkEmailValidation(email)) {
            throw new InvalidFormatException("email address is invalid");

        }
        for (UserAccount user : getDataBase().getUsers()) {
            if (user.getUserName().equals(userName)) {

            }
        }
        String s = checkPasswordStrength(password);
        if(!f) {
            switch (type) {
                case 'L' -> {
                    userAccount = new FreeListener(userName, password, fullName, email, phoneNumber, birthDate);
                    ((Listener) userAccount).setAccountCredit(50);
                    getListenerController().setAccount((Listener) userAccount);
                }
                case 'S' -> {
                    userAccount = new Singer(userName, password, fullName, email, phoneNumber, birthDate, biography);
                    getArtistController().setAccount((Singer) userAccount);
                }
                case 'P' -> {
                    userAccount = new Podcaster(userName, password, fullName, email, phoneNumber, birthDate, biography);
                    getArtistController().setAccount((Podcaster) userAccount);
                }
            }
            setAccount(userAccount);
            getDataBase().getUsers().add(userAccount);
            return "\033[1;40;31m" + s + "\033[1;40;97m" + "\nyou signed up successfully";
        }
        return "no";
    }
    public static boolean checkNumberValidation(String phoneNumber)
    {
        String numberRegex = "^[0][9][0-9][0-9]{8,8}$";
        Pattern pattern1 = Pattern.compile(numberRegex);
        Matcher matcher1 = pattern1.matcher(phoneNumber);
        return matcher1.matches();
    }
    public static boolean checkEmailValidation(String email)
    {
        String emailRegex = "(?!^[.+&'_-]*@.*$)(^[_\\w\\d+&'-]+(\\.[_\\w\\d+&'-]*)*@[\\w\\d-]+(\\.[\\w\\d-]+)*\\.(([\\d]{1,3})|([\\w]{2,}))$)";
        Pattern pattern2 = Pattern.compile(emailRegex);
        Matcher matcher2 = pattern2.matcher(email);
        return matcher2.matches();
    }
}
