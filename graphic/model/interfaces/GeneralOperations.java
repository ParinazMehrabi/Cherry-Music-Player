package org.example.graphic.model.interfaces;

import org.example.graphic.exceptions.*;

import java.text.ParseException;
import java.util.ArrayList;

public interface GeneralOperations
{
    default void backTo() {

    }

    default String logout() {
        return "model";
    }

    default void login(String userName,String password) throws UserNotFoundException, WrongPaswordException {

    }

    default String signup(char type, String userName, String password, String fullName, String email, String phoneNumber, String birthDate, String biography) throws ParseException, InvalidFormatException, ParseException, UserNotFoundException, NotAvailableId {

        return userName;
    }

    default  ArrayList search(String name) throws NotFoundAudio {
        return null;
    }
}
