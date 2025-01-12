package org.example.graphic.model;


import org.example.graphic.model.audio.Music;

import java.util.ArrayList;

public class Album
{
    private long id = 0;
    private static long count = 0;
    private String name;
    private String singer;
    private ArrayList<Music> musicsList;

    public Album(String name, String singer)
    {
        ++count;
        id = count;
        this.name = name;
        this.singer = singer;
        musicsList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public ArrayList<Music> getMusicsList() {
        return musicsList;
    }

    public void setMusicsList(ArrayList<Music> musicsList) {
        this.musicsList = musicsList;
    }
    @Override
    public String toString()
    {
        return "id : " + String.valueOf(id) + " | name : " + name + " | singer : " + singer.toString() + " | musics : " + musicsList.toString();
    }
}
