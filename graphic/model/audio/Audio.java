package org.example.graphic.model.audio;


import org.example.graphic.model.DataBase;
import org.example.graphic.model.Genre;

import java.util.Date;

public class Audio implements Comparable<Audio>
{
    private long id = 0;
    private static long count = 0;
    private String title;
    private String artist;
    private long plays;
    private long likes;
    private java.sql.Date releaseDate;
    private Genre genre;
    private String link;
    private String cover;
    private int score;

    public Audio(String title, String artist, String genre, String link, String cover)
    {
        ++count;
        this.title = title;
        this.link = link;
        this.cover = cover;
        this.plays = 0;
        this.likes = 0;
        id = count;
        this.artist = artist;
        score = 0;
        Date date =new Date();
        this.releaseDate = new java.sql.Date(date.getTime());
        switch (genre)
        {
            case "ROCK" ->
                    this.genre = Genre.ROCK;
            case "POP" ->
                    this.genre = Genre.POP;
            case "JAZZ" ->
                    this.genre = Genre.JAZZ;
            case "HIPHOP" ->
                    this.genre = Genre.HIPHOP;
            case "COUNTRY" ->
                    this.genre = Genre.COUNTRY;
            case "TRUECRIME" ->
                    this.genre = Genre.TRUECRIME;
            case "SOCIETY" ->
                    this.genre = Genre.SOCIETY;
            case "INTERVIEW" ->
                    this.genre = Genre.INTERVIEW;
            case "HISTORY" ->
                    this.genre = Genre.HISTORY;
        }
        DataBase.getDataBase().getAudioFiles().add(this);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getPlays() {
        return plays;
    }

    public void setPlays(long plays) {
        this.plays = plays;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(java.sql.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    @Override
    public String toString()
    {
        return "id : " + String.valueOf(id) + " | title : " + title + " | artist : " + artist + " | plays : " + String.valueOf(plays)+ " | likes : " + String.valueOf(likes) + " | release date : " + releaseDate.toString() + " | genre : " + genre.toString() + " | link : " + link + " | cover : " + cover + score;
    }


    @Override
    public int compareTo(Audio o)
    {
        if(this.title.compareTo((o.getTitle())) < 0)
            return 1;
        if(this.title.compareTo((o.getTitle())) > 0)
            return -1;
        if(this.likes < o.likes)
            return 1;
        if(this.likes > o.likes)
            return -1;
        if(this instanceof Music && o instanceof Podcast)
            return 1;
        if(this instanceof Podcast && o instanceof Music)
            return -1;
        if(this.plays < o.plays)
            return 1;
        if(this.plays > o.plays)
            return -1;
        return 0;
    }
}
