package org.example.graphic.controller;

import javafx.fxml.FXML;
import org.example.graphic.model.Genre;
import org.example.graphic.model.UserAccount;

import java.util.ArrayList;

public class FavGenres
{
    public static FavGenres favGenres;
    public static FavGenres getFavGenres() {
        if (favGenres == null)
            favGenres = new FavGenres();
        return favGenres;
    }
    private ArrayList<Genre> genres ;
    @FXML
    private boolean r1t = false;
    @FXML private boolean hipt= false;
    @FXML private boolean p1t= false;
    @FXML private boolean j1t= false;
    @FXML private boolean t1t= false;
    @FXML private boolean c1t= false;
    @FXML private boolean soci1t= false;
    @FXML private boolean intert= false;
    @FXML private boolean histort= false;

    public ArrayList<Genre> setGenres(boolean r1t, boolean p1t, boolean j1t , boolean t1t, boolean hipt, boolean c1t, boolean soci1t, boolean intert , boolean histort) {
        genres =  new ArrayList<>();
        if (r1t)
            genres.add(Genre.valueOf("ROCK"));
        if (hipt)
            genres.add(Genre.valueOf("HIPHOP"));
        if (p1t)
            genres.add(Genre.valueOf("POP"));
        if (j1t)
            genres.add(Genre.valueOf("JAZZ"));
        if (t1t)
            genres.add(Genre.valueOf("TRUECRIME"));
        if (c1t)
            genres.add(Genre.valueOf("COUNTRY"));
        if (soci1t)
            genres.add(Genre.valueOf("SOCIETY"));
        if (intert)
            genres.add(Genre.valueOf("INTERVIEW"));
        if (histort)
            genres.add(Genre.valueOf("HISTORY"));
        return genres;
    }
}
