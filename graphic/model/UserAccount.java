package org.example.graphic.model;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class UserAccount
{
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private java.sql.Date birthDate;

    public UserAccount(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        java.util.Date date =new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        this.birthDate=new java.sql.Date(date.getTime());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }
    @Override
    public String toString()
    {
        return "username : " + userName + " | password : " + password + " | full name : " + fullName + " | email address : " + email + " | phone number : "
                + phoneNumber + " | birthdate : " + String.valueOf(birthDate);
    }
}
