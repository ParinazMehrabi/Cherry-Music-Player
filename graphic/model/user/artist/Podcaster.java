package org.example.graphic.model.user.artist;


import org.example.graphic.model.audio.Podcast;

import java.text.ParseException;
import java.util.ArrayList;

public class Podcaster extends Artist
{
    private ArrayList<Podcast> podcasts;

    public Podcaster(String userName, String password, String fullName, String email, String phoneNumber, String birthDate, String biography) throws ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate, biography);
        podcasts = new ArrayList<>();
    }

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }
    @Override
    public String toString()
    {
        //podcasts.toString()
        return super.toString() + " | podcasts : " + podcasts.size();
    }
}
