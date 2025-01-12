package org.example.graphic.controller;
import org.example.graphic.exceptions.NotFoundAudio;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.interfaces.GeneralOperations;
import org.example.graphic.model.user.artist.Artist;
import java.util.ArrayList;
import java.util.Objects;
import static org.example.graphic.model.DataBase.getDataBase;
public class SearchController implements GeneralOperations
{
    private static SearchController searchController;
    public static SearchController getSearchController() {
        if (searchController == null)
            searchController = new SearchController();
        return searchController;
    }
    @Override
    public ArrayList search(String name) throws NotFoundAudio {
        ArrayList<Audio> titles = new ArrayList<>();
        titles = searchInAudioFiles(titles,name);
        ArrayList<Artist> artists = new ArrayList<>();
        artists = searchInArtists(artists,name);
        if(artists.isEmpty() && titles.isEmpty())
        {
            throw new NotFoundAudio();
        }
        if(artists.isEmpty()) {
            return titles;
        }
        else if(titles.isEmpty())
        {
            return artists;
        }
        return null;
    }
    public ArrayList<Audio> searchInAudioFiles(ArrayList<Audio> titles, String name)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(Objects.equals(audio.getTitle(), name))
            {
                titles.add(audio);
            }
        }
        return titles;
    }
    public ArrayList<Artist> searchInArtists(ArrayList<Artist> artists, String name)
    {
        for(UserAccount userAccount : getDataBase().getUsers()) {
            if (userAccount instanceof Artist) {
                if (Objects.equals(userAccount.getUserName(), name)) {
                    artists.add((Artist) userAccount);
                }
            }
        }
        return artists;
    }
}
