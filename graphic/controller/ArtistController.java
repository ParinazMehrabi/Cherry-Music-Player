package org.example.graphic.controller;


import org.example.graphic.model.Album;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Music;
import org.example.graphic.model.audio.Podcast;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;

import static org.example.graphic.model.DataBase.getDataBase;

public class ArtistController
{
    private static ArtistController artistController;

    private Artist account;

    private ArtistController() {
    }
    public static ArtistController getArtistController() {
        if (artistController == null)
            artistController = new ArtistController();
        return artistController;
    }

    public Artist getAccount()
    {
        return this.account;
    }

    public void setAccount(Artist account)
    {
        this.account = account;
    }
    public void login(Artist artist)
    {
        setAccount(artist);
    }
    public String showFollowers()
    {
        StringBuilder followers = new StringBuilder();
        for(UserAccount userAccount : account.getFollowers())
        {
            followers.append(".").append(userAccount.getUserName()).append("\n");
        }
        if(followers.isEmpty())
        {
            return "you have no follower yet";
        }
        followers.deleteCharAt(followers.length() - 1);
        return followers.toString();
    }
    public String newAlbum(String name)
    {
        for(UserAccount userAccount : getDataBase().getUsers())
        {
            if(userAccount instanceof Singer && userAccount == account)
            {
                ((Singer) userAccount).getAlbums().add(new Album(name,account.getUserName()));
            }
        }
        return "album has been created";
    }
    public String publish(char type, String title, String genre,String lOrC, String link, String cover)
    {
        if(type == 'M')
        {
            return publishMusic(title,genre,lOrC,link,cover);
        }
        else if(type == 'P')
        {
            return publishPodcast(title,genre,lOrC,link,cover);
        }
       return "please enter M or P";
    }
    public String publishMusic(String title, String genre,String lOrC, String link, String cover)
    {
        if(account instanceof Podcaster)
        {
            return "you can not release a music";
        }
        Music music = new Music(title,account.getUserName(),genre,link,cover,lOrC);
        getDataBase().getAudioFiles().add(music);
        this.account.getReleases().add(music);
        return "published";
    }
    public String publishPodcast(String title, String genre,String lOrC, String link, String cover)
    {
        if(account instanceof Singer)
        {
            return "you can not release a podcast";
        }
        Podcast podcast = new Podcast(title,account.getUserName(),genre,link,cover,lOrC);
        getDataBase().getAudioFiles().add(podcast);
        this.account.getReleases().add(podcast);
        ((Podcaster) account).getPodcasts().add(podcast);
        return "podcast has been published successfully";
    }
    public String calculateEarning()
    {
        if(account instanceof Singer)
        {
            return calculateEarning((Singer) account);
        }
        else if(account instanceof Podcaster)
        {
            return calculateEarning((Podcaster) account);
        }
        return null;
    }
    public String calculateEarning(Singer singer)
    {
        long playtimes = 0;
            for(Album album : singer.getAlbums())
            {
                for (Music music : album.getMusicsList()) {
                    playtimes += music.getPlays();
                }
            }
        singer.setIncome(playtimes * 0.4);
            return "you earned " + String.valueOf(singer.getIncome());
    }
    public String calculateEarning(Podcaster podcaster)
    {
        long playtimes = 0;
        for(Podcast podcast : podcaster.getPodcasts())
        {
            playtimes += podcast.getPlays();
        }
        podcaster.setIncome(playtimes * 0.5);
        return "you earned " + String.valueOf(podcaster.getIncome());
    }
    public StringBuilder showStatistics()
    {
        StringBuilder file = new StringBuilder();
        if(account instanceof Singer)
        {
            file = singerStatistics(file);
        }
        else if(account instanceof Podcaster)
        {
            file = podcasterStatistics(file);
        }
        if(file.isEmpty())
            return new StringBuilder("you havent released anything yet");
        file.deleteCharAt(file.length() - 1);
        return file;
    }
    public StringBuilder singerStatistics(StringBuilder file)
    {
        if(account instanceof Singer)
        {
            for(Album album : ((Singer) account).getAlbums())
            {
                for(Music music : album.getMusicsList())
                {
                    file.append(".").append(music.getTitle()).append(" -> album: ").append(album.getName()).append(" | plays: ").append(music.getPlays()).append("\n");
                }
            }
        }
        return file;
    }
    public StringBuilder podcasterStatistics(StringBuilder file)
    {
        for(Podcast podcast : ((Podcaster) account).getPodcasts())
        {
            file.append(".").append(podcast.getTitle()).append(" -> plays: ").append(podcast.getPlays()).append("\n");
        }
        return file;
    }
    public StringBuilder help()
    {
        StringBuilder orders = new StringBuilder();
        if(account instanceof Singer)
        {
            orders.append(".Signup -> Signup-S-[username]-[password]-[name]-[email]-[phone number]-[birth date]-[bio]\n.login -> Login-[username]-[password]\n.logout -> Logout\n.see accountInfo -> AccountInfo\n");
            orders.append(".see followers -> Followers\n.see all musics' play times -> ViewsStatistics\n.show income -> CalculateEarnings\n.release new album -> NewAlbum-[name]\n.publish new music -> Publish-M-[title]-[genre]-[lyric|caption]-[link]-[cover]-[album ID]");
        }
        else if(account instanceof  Podcaster)
        {
            orders.append(".Signup -> Signup-P-[username]-[password]-[name]-[email]-[phone number]-[birth date]-[bio]\n.login -> Login-[username]-[password]\n.logout -> Logout\n.see accountInfo -> AccountInfo\n");
            orders.append(".see followers -> Followers\nsee all podcasts' play times -> ViewsStatistics\n.show income -> CalculateEarnings\n.publish new podcast -> Publish-P-[title]-[genre]-[lyric|caption]-[link]-[cover]");
        }
        return orders;
    }
    public String showArtistInfo()
    {
        return account.toString();
    }
}
