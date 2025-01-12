package org.example.graphic.model.user.listener;

import java.text.ParseException;

public class FreeListener extends Listener
{
    private final int addToPlaylistLimit;
    private final int PlaylistLimit;
    public FreeListener(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate);
        super.setPremiumDeadline(null);
        addToPlaylistLimit = 10;
        PlaylistLimit = 3;
        this.setPremiumDeadline(null);
    }

    public int getAddToPlaylistLimit()
    {
        return addToPlaylistLimit;
    }
    public int getPlaylistLimit()
    {
        return PlaylistLimit;
    }
    @Override
    public String toString()
    {
        return super.toString() + " | add to playlist limit : " + String.valueOf(addToPlaylistLimit) + " | PlaylistLimit : " + String.valueOf(PlaylistLimit);
    }
}
