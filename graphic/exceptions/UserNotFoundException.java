package org.example.graphic.exceptions;

public class UserNotFoundException extends FailedLoginException
{
    public UserNotFoundException()
    {
        super("this username doesnt exist");
    }
}
