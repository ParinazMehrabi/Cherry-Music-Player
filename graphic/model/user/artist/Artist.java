package org.example.graphic.model.user.artist;


import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Audio;

import java.text.ParseException;
import java.util.ArrayList;

public class Artist extends UserAccount
{
    private double income;
    private ArrayList<UserAccount> followers;

    public ArrayList<Audio> getReleases() {
        return releases;
    }

    public void setReleases(ArrayList<Audio> releases) {
        this.releases = releases;
    }

    private ArrayList<Audio> releases;
    private String biography;

    public Artist(String userName, String password, String fullName, String email, String phoneNumber, String birthDate,String biography) throws ParseException, ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate);
        this.followers = new ArrayList<>();
        this.biography = biography;
        income =0;
        releases = new ArrayList<>();
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public ArrayList<UserAccount> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<UserAccount> followers) {
        this.followers = followers;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    @Override
    public String toString()
    {
        return super.toString() + " | income : " + income + " | followers : " + followers.size() + " | biography : " + biography;
    }
}
