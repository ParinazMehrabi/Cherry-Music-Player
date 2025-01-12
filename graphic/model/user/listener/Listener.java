package org.example.graphic.model.user.listener;


import org.example.graphic.model.Genre;
import org.example.graphic.model.Playlist;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.user.artist.Artist;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Listener extends UserAccount
{
    private double accountCredit;
    private ArrayList<Playlist> playlists;
    private Map<Audio,Integer> playTimes ;
    private ArrayList<Audio> likedAudioFiles;
    private Date premiumDeadline;
    private ArrayList<Genre> favouriteGenres;
    private ArrayList<Artist> followings;
    public Listener(String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate);
        playlists = new ArrayList<>();
        playTimes = new HashMap<>();
        favouriteGenres = new ArrayList<>();
        Date date = new Date();
        this.premiumDeadline = null;
        likedAudioFiles = new ArrayList<>();
        followings = new ArrayList<>();
        accountCredit = 50;
    }

    public double getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(double accountCredit) {
        this.accountCredit = accountCredit;
    }


    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Map<Audio, Integer> getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(Map<Audio, Integer> playTimes) {
        this.playTimes = playTimes;
    }

    public Date getPremiumDeadline() {
        return premiumDeadline;
    }

    public void setPremiumDeadline(Date premiumDeadline) {
        this.premiumDeadline = premiumDeadline;
    }

    public ArrayList<Genre> getFavouriteGenres() {
        return favouriteGenres;
    }

    public ArrayList<Audio> getLikedAudioFiles() {
        return likedAudioFiles;
    }

    public void setLikedAudioFiles(ArrayList<Audio> likedAudioFiles) {
        this.likedAudioFiles = likedAudioFiles;
    }
    public void setFavouriteGenres(ArrayList<Genre> favouriteGenres) {
        this.favouriteGenres = favouriteGenres;
    }

    public ArrayList<Artist> getFollowings() {
        return followings;
    }

    public void setFollowings(ArrayList<Artist> followings) {
        this.followings = followings;
    }
    @Override
    public String toString()
    {
        return super.toString() + " | accountCredit : " + accountCredit + " | playlists : " + playlists.toString() + " | play times : " + playTimes.toString() + " | likedAudioFiles : " + likedAudioFiles+ " | premiumDeadline : "
                + premiumDeadline + " | favourite genres : " + favouriteGenres.toString() + " | followings : " + followings.size();
    }
}
