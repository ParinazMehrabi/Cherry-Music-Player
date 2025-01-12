package org.example.graphic.model.user;


import org.example.graphic.model.UserAccount;

import java.text.ParseException;

import static org.example.graphic.model.DataBase.getDataBase;

public class Admin extends UserAccount
{
    private static Admin admin;
    public Admin(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException, ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate);
    }
    public static Admin getAdmin(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException {
        if (admin == null) {
            admin = new Admin(userName,password,fullName,email,phoneNumber,birthDate);
            getDataBase().getUsers().add(admin);
            return admin;
        } else {
            return getAdmin();
        }
    }

    public static Admin getAdmin() {
        return admin;
    }
    @Override
    public String toString()
    {
        return super.toString();
    }
}
