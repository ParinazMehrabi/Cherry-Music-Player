package org.example.graphic.model;


import org.example.graphic.model.audio.Audio;

import java.util.ArrayList;
import java.util.Iterator;

public class Playlist implements Iterable<Audio>
{
    private long id;
    static int count = 0;
    private String name;
    private String curator;
    private ArrayList<Audio> audiosList;
    private int index = 0;

    public Playlist(String name, String curator)
    {
        ++count;
        id = count;
        this.name = name;
        this.curator = curator;
        this.audiosList = new ArrayList<>();
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

    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    public ArrayList<Audio> getAudiosList() {
        return audiosList;
    }

    public void setAudiosList(ArrayList<Audio> audiosList) {
        this.audiosList = audiosList;
    }
    @Override
    public String toString()
    {
        return "id : " + id + " | name : " + name + " | curator : " + curator + " | audio list: " + audiosList.toString();
    }
    @Override
    public Iterator<Audio> iterator() {
        return new Iterator<Audio>() {
            @Override
            public boolean hasNext() {
                return index < audiosList.size();
            }

            @Override
            public Audio next() {
                if (hasNext())
                    return audiosList.get(index++);
                else
                    return null;
            }
        };
    }

//    @Override
//    public Iterator<Audio> iterator() {
//        return new Iterator<>() {
//            @Override
//            public boolean hasNext() {
//                if (index < audiosList.size())
//                    return true;
//                return false;
//            }
//
//            @Override
//            public Audio next() {
//                if (hasNext()) {
//                    return audiosList.get(index++);
//                }
//                return null;
//            }
//
//        };
//    }

}
