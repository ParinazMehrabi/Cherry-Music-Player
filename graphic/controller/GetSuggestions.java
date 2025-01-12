package org.example.graphic.controller;

import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.listener.Listener;

import java.util.ArrayList;
import java.util.Collections;

import static org.example.graphic.model.DataBase.getDataBase;

public class GetSuggestions
{
    private Listener account;

    public void setAccount(Listener account) {
        this.account = account;
    }

    public ArrayList<Audio> giveSuggestions()
    {
        ArrayList<Audio> audios= new ArrayList<>();
        for(Audio audio : getDataBase ().getAudioFiles())
        {
            audio.setScore(0);
            if(account.getLikedAudioFiles().contains(audio))
            {
                audio.setScore(audio.getScore() + 1);
            }
            if(account.getFavouriteGenres().contains(audio.getGenre()))
            {
                System.out.println(account.getFavouriteGenres().contains(audio.getGenre()));
                audio.setScore(audio.getScore() + 1);
            }
            for(Artist artist: account.getFollowings())
            {
                if(artist.getUserName().equals(audio.getArtist()))
                {
                    audio.setScore(audio.getScore() + 1);
                }
            }
        }
        for(Audio audio : getDataBase ().getAudioFiles())
        {
            if(audio.getScore() > 0)
            {
                audios.add(audio);
            }
        }
        for (int i = 0; i < audios.size(); i++)
        {
            for (int j = i + 1; j < audios.size(); j++)
            {
                if (audios.get(j).getScore() > audios.get(i).getScore()) {
                    Collections.swap(audios,j,i);
                }
            }
        }
//        while(n!= 0)
//        {
//            audios.remove(audios.getLast());
//            --n;
//        }
        return audios;
    }
}
