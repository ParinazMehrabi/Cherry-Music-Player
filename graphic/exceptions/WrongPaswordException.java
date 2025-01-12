package org.example.graphic.exceptions;

public class WrongPaswordException extends FailedLoginException
{
    public WrongPaswordException()
    {
        super("password is incorrect");
    }
}
