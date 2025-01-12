package org.example.graphic.controller;
import org.example.graphic.exceptions.UserNotFoundException;
import org.example.graphic.exceptions.WrongPaswordException;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.interfaces.GeneralOperations;

import static org.example.graphic.model.DataBase.getDataBase;

public class LoginController implements GeneralOperations
{
    UserAccount account;

    public void setAccount(UserAccount account) {
        this.account = account;
    }
    @Override
    public void login(String userName,String password) throws UserNotFoundException, WrongPaswordException
    {
        for (UserAccount userAccount : getDataBase().getUsers()) {
            boolean truth = false;
            boolean truth2 = false;
            for (UserAccount user : getDataBase().getUsers()) {
                if (user.getUserName().equals(userName)) {
                    truth = true;
                    if (user.getPassword().equals(password)) {
                        truth2 = true;
                        userAccount = user;
                    }
                }
            }
            if (!truth) {
                throw new UserNotFoundException();
            }
            if (!truth2) {
                throw new WrongPaswordException();
            }
            setAccount(userAccount);
        }
    }
}
