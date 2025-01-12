package org.example.graphic.model.user.artist;


import org.example.graphic.model.Album;
import org.example.graphic.model.audio.Music;

import java.text.ParseException;
import java.util.ArrayList;

public class Singer extends Artist
{
    private ArrayList<Album> albums;
    private ArrayList<Music> musics;

    public Singer(String userName, String password, String fullName, String email, String phoneNumber, String birthDate, String biography) throws ParseException {
        super(userName, password, fullName, email, phoneNumber, birthDate, biography);
        albums = new ArrayList<>();
        musics = new ArrayList<>();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    @Override
    public String toString()
    {
        //or albums
        return super.toString() + " | albums : " + albums.size();
    }
}
