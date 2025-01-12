package org.example.graphic.controller;

import org.example.graphic.model.DataBase;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.Listener;

import java.util.ArrayList;

import static org.example.graphic.model.DataBase.getDataBase;

public class ArtistsController {
    ArrayList<Artist> artists = new ArrayList<Artist>();
   public ArrayList<Artist> artistInfo()
   {
       for(UserAccount userAccount : getDataBase().getUsers())
       {
           if(userAccount instanceof Singer)
           {
               artists.add(((Artist) userAccount));
           }
           if(userAccount instanceof Podcaster)
           {
               artists.add(((Artist) userAccount));
           }
       }
       return artists;
   }
}
