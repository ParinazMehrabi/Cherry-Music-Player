package org.example.graphic.controller;
import org.example.graphic.exceptions.InvalidFormatException;
import org.example.graphic.exceptions.NotAvailableId;
import org.example.graphic.exceptions.UserNotFoundException;
import org.example.graphic.exceptions.WrongPaswordException;
import org.example.graphic.model.UserAccount;
import static org.example.graphic.controller.UserController.checkEmailValidation;
import static org.example.graphic.controller.UserController.checkNumberValidation;
import static org.example.graphic.model.DataBase.getDataBase;

public class ExceptionHandler {
    public static ExceptionHandler exceptionHandler;

    public static ExceptionHandler getExceptionHandler() {
        if (exceptionHandler == null)
            exceptionHandler = new ExceptionHandler();
        return exceptionHandler;
    }

    public void checkUsername(String username) throws UserNotFoundException, NotAvailableId {
        boolean f = false;
        for (UserAccount user : getDataBase().getUsers()) {
            if (user.getUserName().equals(username)) {
                throw new UserNotFoundException();
            }
        }

    }

    public void NumberValidation(String phoneNumber) throws InvalidFormatException {
        if (!checkNumberValidation(phoneNumber)) {
            throw new InvalidFormatException("phone number is invalid");
        }
    }

    public void EmailValidation(String email) throws InvalidFormatException {
        if (!checkEmailValidation(email)) {
            throw new InvalidFormatException("email address is invalid");

        }
    }

    public void emptyCheck(String text) {
        if (text.isEmpty())
            throw new NullPointerException();
    }
    public void passwordCheck(String username,String password) throws UserNotFoundException, WrongPaswordException {
        boolean uC = false;
        boolean pC = false;
        for(int i = 0;i< getDataBase().getUsers().size();++i) {
            if (getDataBase().getUsers().get(i).getUserName().equals(username))
            {
                uC = true;
                if(getDataBase().getUsers().get(i).getPassword().equals(password))
                {
                    pC = true;
                }
            }
        }
        if(!uC)
        {
            throw new UserNotFoundException();
        }
        if(!pC)
            throw new WrongPaswordException();
    }

    public static String PasswordStrength(String password) {
        if (password.matches("\"(?=.*[!@#$%^/:;<=>?_*.A-Za-z0-9]).*\"")) {
            return "your password is strong";
        } else if (password.matches("(?=.*[0-9]|[A-Za-z]).*") | password.matches("(?=.*[!@#$%^&*.]).*|[A-Za-z]") | (password.matches("(?=.*[!@#$%^&*.]).*|[0-9]"))) {
            return "your password is weak";
        }
        return null;
    }
}
