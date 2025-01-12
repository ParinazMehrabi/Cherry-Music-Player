package org.example.graphic.model.user.listener;

import java.text.ParseException;

public class PremiumListener extends Listener
{
    private int leftDays;
    public PremiumListener(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException
    {
        super(userName, password, fullName, email, phoneNumber, birthDate);
        leftDays = 0;
    }
    public int getLeftDays()
    {
        return leftDays;
    }

    public void setLeftDays(int leftDays) {
        this.leftDays = leftDays;
    }
    @Override
    public String toString()
    {
        return super.toString() + " | left days : " + String.valueOf(leftDays);
    }
}
