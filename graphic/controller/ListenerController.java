package org.example.graphic.controller;


import org.example.graphic.exceptions.*;
import org.example.graphic.model.*;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.audio.Music;
import org.example.graphic.model.audio.Podcast;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.Listener;
import org.example.graphic.model.user.listener.PremiumListener;

import java.text.ParseException;
import java.util.*;

import static java.lang.String.valueOf;
import static org.example.graphic.model.DataBase.getDataBase;

public class ListenerController
{
    private static ListenerController listenerController;

    private Listener account;

    private ListenerController() {
    }
    public static ListenerController getListenerController() {
        if (listenerController == null)
            listenerController = new ListenerController();
        return listenerController;
    }

    public Listener getAccount()
    {
        return account;
    }

    public void setAccount(Listener account)
    {
        this.account = account;
    }
    public String addToFavouriteGenres(String[] favGenres)
    {
        if(favGenres.length > 4)
        {
            return "\033[1;40;31m" + "You have exceeded the limit";
        }
        else
        {
            for(int i=0;i<favGenres.length;++i)
            {
                account.getFavouriteGenres().add(Genre.valueOf(favGenres[i]));
            }
        }
        return "Genres added to your favourite list";
    }
    public void login(Listener listener)
    {
        setAccount(listener);
    }
    public String showAllGenres() {
        StringBuilder genres = new StringBuilder();
        Genre[] genre = Genre.values();
        for (Genre genres1 : genre) {
            genres.append(".").append(genres1.name()).append("\n");
        }
        genres.deleteCharAt(genres.length() - 1);
        return "\033[1;40;97m" + genres;
    }
    public StringBuilder search(String name)
    {
        StringBuilder titles = new StringBuilder();
        titles = searchInAudioFiles(titles,name);
        StringBuilder artists = new StringBuilder();
        artists = searchInArtists(artists,name);
        if(artists.isEmpty() && titles.isEmpty())
        {
            return new StringBuilder(" there is no such music or artist");
        }
        if(artists.isEmpty()) {
            titles.deleteCharAt(titles.length() - 1);
            return titles;
        }
        else if(titles.isEmpty())
        {
            artists.deleteCharAt(artists.length() - 1);
            return artists;
        }
        return null;
    }
    public StringBuilder searchInAudioFiles(StringBuilder titles, String name)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(Objects.equals(audio.getTitle(), name))
            {
                titles.append(".").append(audio.toString()).append("\n");
            }
        }
        return titles;
    }
    public StringBuilder searchInArtists(StringBuilder artists, String name)
    {
        for(UserAccount userAccount : getDataBase().getUsers()) {
            if (userAccount instanceof Artist) {
                if (Objects.equals(userAccount.getFullName(), name)) {
                    artists.append(".").append(userAccount.toString()).append("\n");
                }
            }
        }
        return artists;
    }
    public String playAudioFile(long audioId)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getId() == audioId)
            {
                audio.setPlays(audio.getPlays() + 1);
                boolean truth = false;
                if(account.getPlayTimes().containsKey(audio))
                {
                    account.getPlayTimes().put(audio, account.getPlayTimes().get(audio) + 1);
                }
                else
                {
                    account.getPlayTimes().put(audio,0);
                }
                return audio.toString();
            }
        }
        return "id is invalid";
    }
    public ArrayList<Audio> sortAudioFiles(char base)
    {
        ArrayList<Audio> sortedAudioFiles = new ArrayList<>(getDataBase().getAudioFiles());
        if(base == 'L') {
            sortedAudioFiles = sortByLike(sortedAudioFiles);
            //           sortedAudioFiles = getDataBase().getAudioFiles();
//            if(sortedAudioFiles.isEmpty()) {
//                return new StringBuilder("there is no audio file");
//            }
            return sortedAudioFiles;
        }
        else if(base == 'P') {
            {
                sortedAudioFiles = (ArrayList<Audio>) sortedAudioFiles.clone();
                sortedAudioFiles = sortByPlays(sortedAudioFiles);
            }
//            if (sortedAudioFiles.isEmpty())
//            {
//                return new StringBuilder("there is no audio file");
//            }
            return sortedAudioFiles;
        }
        else
        {
            sortedAudioFiles = sort(sortedAudioFiles);
            return sortedAudioFiles;
        }
    }
    public ArrayList<Audio> sortByLike(ArrayList<Audio> sortedAudioFiles)
    {
        for (int i = 0; i < getDataBase().getAudioFiles().size(); i++) {
            for (int j = i + 1; j < getDataBase().getAudioFiles().size(); j++)
            {
                if (sortedAudioFiles.get(j).getLikes() > sortedAudioFiles.get(i).getLikes()) {
                    Collections.swap(sortedAudioFiles, i,j);
                }
            }
        }
        return sortedAudioFiles;
    }
    public ArrayList<Audio> sortByPlays(ArrayList<Audio> sortedAudioFiles)
    {
        for (int i = 0; i < getDataBase().getAudioFiles().size(); i++) {
            for (int j = i+1; j < getDataBase().getAudioFiles().size(); j++)
            {
                if (sortedAudioFiles.get(j).getPlays() > sortedAudioFiles.get(i).getPlays())
                {
                    Collections.swap(sortedAudioFiles, i, j);
                }
            }
        }
        return sortedAudioFiles;
    }
    public ArrayList<Audio> sort(ArrayList<Audio> sortedAudioFiles)
    {
        for (int i = 0; i < sortedAudioFiles.size(); i++) {
            for (int j = i + 1; j < sortedAudioFiles.size(); j++) {
                if (sortedAudioFiles.get(j).compareTo(sortedAudioFiles.get(i)) > 0) {
                    Audio tmp = sortedAudioFiles.get(i);
                    sortedAudioFiles.set(i, sortedAudioFiles.get(j));
                    sortedAudioFiles.set(j, tmp);
                }
            }
        }
        return sortedAudioFiles;
    }
    public ArrayList<Audio> filterAudioFiles(char base, String filterBy) {
        ArrayList<Audio> filteredAudioFiles = new ArrayList<>();
        if (base == 'A')
        {filteredAudioFiles = filterByArtist(filteredAudioFiles,filterBy);
//            if(filteredAudioFiles.isEmpty())
//            {
//                return "no audio file was published by this artist";
//            }
//            else
            {
                return filteredAudioFiles;
            }

        }
        if (base == 'G')
        {filteredAudioFiles = filterByGenre(filteredAudioFiles,filterBy);
            {
                return filteredAudioFiles;}
        }
        if (base == 'D') {
            filteredAudioFiles = filterByDate(filteredAudioFiles,filterBy);
            {
                return filteredAudioFiles;}}
        return null;
    }
    public ArrayList<Audio> filterByArtist(ArrayList<Audio> filteredAudioFiles, String filterBy)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getArtist().equals(filterBy))
            {
                filteredAudioFiles.add(audio);
            }
        }
        return filteredAudioFiles;
    }
    public ArrayList<Audio> filterByGenre(ArrayList<Audio> filteredAudioFiles,String filterBy)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getGenre() != null && audio.getGenre() != null) {
                if (audio.getGenre().toString() == filterBy) {
                    filteredAudioFiles.add(audio);
                }
            }
        }
        return filteredAudioFiles;
    }
    public ArrayList<Audio> filterByDate(ArrayList<Audio> filteredAudioFiles, String filterBy)
    {
        filterBy = filterBy.replace('/','-');
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getReleaseDate().toString().equals(filterBy))
            {
                filteredAudioFiles.add(audio);
            }
        }
        return filteredAudioFiles;
    }
    public String inBetweenTwoDates(String fDate, String sDate) throws ParseException {
        fDate = fDate.replace('/','-');
        sDate = sDate.replace('/','-');
        StringBuilder filteredAudioFiles= new StringBuilder();
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getReleaseDate().before(java.sql.Date.valueOf(sDate)) && audio.getReleaseDate().after(java.sql.Date.valueOf(fDate)) || (audio.getReleaseDate().equals(java.sql.Date.valueOf(fDate)) || audio.getReleaseDate().equals(java.sql.Date.valueOf(sDate))))
            {
                filteredAudioFiles.append(".").append(audio).append("\n");
            }
        }
        if(filteredAudioFiles.isEmpty())
            return "no audio was published between these dates";
        else
            filteredAudioFiles.deleteCharAt(filteredAudioFiles.length() - 1);
        return filteredAudioFiles.toString();
    }
    //    public Artist findArtist(String userName)
//    {
//        for(UserAccount userAccount : getDataBase().getUsers())
//        {
//            if(Objects.equals(userAccount.getUserName(), userName))
//            {
//                return (Artist) userAccount;
//            }
//    }
    public String Followings()
    {
        if(account.getFollowings().isEmpty())
        {
            return "you haven't followed anyone yet!";
        }
        StringBuilder followings = new StringBuilder();
        for(Artist artist : account.getFollowings())
        {
            followings.append(".").append(showArtistInfo(artist)).append("\n");
        }
        followings.deleteCharAt(followings.length() - 1);
        return valueOf(followings);
    }
    public String recordReport(String userName,String explanation)
    {
        for(UserAccount userAccount : getDataBase().getUsers()) {
            if (userName.equals(userAccount.getUserName())) {
                getDataBase().getReports().add(new Report(account, (Artist) userAccount, explanation));
                return "your report has been recorded successfully";
            }
        }
        return "username is not valid";
    }
    public StringBuilder showArtistInfo(Artist artist)
    {
        StringBuilder info = new StringBuilder();
        info.append("username : ").append(artist.getUserName()).append(" | full name : ").append(artist.getFullName()).append(" | email address : ").append(artist.getEmail()).append(" | birthdate : ").append(valueOf(artist.getBirthDate())).append(" | followers: ").append(artist.getFollowers().size());
        if(artist instanceof Singer)
        {
            info.append(" | albums: ");
            info.append(((Singer) artist).getAlbums().size());
        }
        else if(artist instanceof Podcaster)
        {
            info.append(" | podcasts: ");
            info.append(((Podcaster) artist).getPodcasts().size());
        }
        return info;
    }
    public String artistsInfo()
    {
        StringBuilder singers = new StringBuilder();
        StringBuilder podcasters = new StringBuilder();
        for(UserAccount userAccount : getDataBase().getUsers())
        {
            if(userAccount instanceof Singer)
            {
                singers.append(".").append(showArtistInfo((Artist) userAccount).append("\n"));
            }
            if(userAccount instanceof Podcaster)
            {
                podcasters.append(".").append(showArtistInfo((Artist) userAccount).append("\n"));
            }
        }
        if(podcasters.isEmpty() && singers.isEmpty())
        {
        return "there is no artist yet";
        }
        if(podcasters.isEmpty())
        {
            podcasters.append("list is empty");
        }
        if(singers.isEmpty())
        {
            singers.append("list is empty");
        }
        podcasters.deleteCharAt(podcasters.length() - 1);
        return "list of singers: \n" + valueOf(singers) + ".list of podcasters: \n" + valueOf(podcasters);
    }
    public StringBuilder showSelectedArtist(String userName)
    {
        for(UserAccount artist : getDataBase().getUsers())
        {
            if(artist.getUserName().equals(userName))
            {
                return showArtistInfo((Artist) artist);
            }
        }
        return new StringBuilder("id is invalid");
    }
    public String showAccountInfo() throws ParseException {
        String answer = account.toString();
        if(account instanceof PremiumListener)
        {
            ((PremiumListener)account).setLeftDays(((PremiumListener)account).getLeftDays() - 1);
            account.setPremiumDeadline(addDays(account.getPremiumDeadline(),-1));
            if(((PremiumListener)account).getLeftDays() == 0)
            {
                getDataBase().getUsers().remove(account);
                String date = account.getBirthDate().toString();
                date = date.replace('-','/');
                FreeListener freeListener = new FreeListener(account.getUserName(), account.getPassword(), account.getFullName(), account.getEmail(), account.getPhoneNumber(),date);
                freeListener.setBirthDate(account.getBirthDate());
                freeListener.setFavouriteGenres(account.getFavouriteGenres());
                freeListener.setLikedAudioFiles(account.getLikedAudioFiles());
                freeListener.setPlaylists(account.getPlaylists());
                freeListener.setFollowings(account.getFollowings());
                freeListener.setPlayTimes(account.getPlayTimes());
                setAccount(freeListener);
                getDataBase().getUsers().add(freeListener);
            }
        }
        return answer;
    }
    public String createPlaylist(String playlistName) throws FreeAccountLimitException {
        if(account instanceof FreeListener)
        {
            return createPlaylist((FreeListener) account,playlistName);
        }
        else
            return createPlaylist((PremiumListener) account,playlistName);
    }
    public String createPlaylist(FreeListener freeListener,String playlistName) throws FreeAccountLimitException {
        if(freeListener.getPlaylists().size() >= 3)
        {
            throw new FreeAccountLimitException("you have reached you limit");
        }
        Playlist playlist = new Playlist(playlistName,account.getUserName());
        freeListener.getPlaylists().add(playlist);
        return playlistName + " playlist has been created successfully";
    }
    public String createPlaylist(PremiumListener premiumListener, String playlistName)
    {
        Playlist playlist = new Playlist(playlistName,account.getUserName());
        premiumListener.getPlaylists().add(playlist);
        return playlistName + " playlist has been created successfully";
    }
    public Audio findAudio(long audioId)
    {
        for(Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getId() == audioId)
            {
                return audio;
            }
        }
        return null;
    }
    public String addToPlaylist(String playlistName,long audioId) throws FreeAccountLimitException, AddedBefore, NonExistedPlaylst {
        if(account instanceof FreeListener)
        {
            return addToPlaylist((FreeListener) account,playlistName,audioId);
        }
        else
        {
            return addToPlaylist((PremiumListener) account,playlistName,audioId);
        }
    }
    public String addToPlaylist(FreeListener freeListener,String playlistName,long audioId) throws FreeAccountLimitException, AddedBefore, NonExistedPlaylst {

            for (Playlist playlist : freeListener.getPlaylists())
            {
            if (playlist.getName().equals(playlistName))
            {
                if (playlist.getAudiosList().size() >= 10) {
                    throw new FreeAccountLimitException("you have reached you limit");
                }
                else {
                    if (findAudio(audioId) == null) {
                        return "audio id is invalid";
                    }
                    for (Playlist playlists : freeListener.getPlaylists()) {
                        for (Audio audio : playlists.getAudiosList()) {
                            if (findAudio(audioId).getTitle().equals(audio.getTitle())) {
                                throw new AddedBefore("this music has been added before");
                            }
                        }
                    }
                    playlist.getAudiosList().add(findAudio(audioId));
                    return findAudio(audioId).getTitle() + "has been added to " + playlistName + " successfully";
                }
            }
        }
        throw new NonExistedPlaylst("there is no such playlist");
    }
    public String addToPlaylist(PremiumListener premiumListener,String playlistName,long audioId)
    {
        for (Playlist playlist : premiumListener.getPlaylists())
        {
            if(playlist.getName().equals(playlistName))
            {
                playlist.getAudiosList().add(findAudio(audioId));
            }
        }
        return "audio has been added to " + playlistName + " successfully";
    }
    public StringBuilder showPlaylists()
    {
        StringBuilder playlists = new StringBuilder();
        for(Playlist playlist : account.getPlaylists())
        {
            playlists.append(".").append(playlist.toString()).append("\n");
        }
        if(playlists.isEmpty())
            return new StringBuilder("there is no playlist yet");
        playlists.deleteCharAt(playlists.length() - 1);
        return playlists;
    }
    public String showSelectedPlaylist(String playlistName)
    {
        StringBuilder playlists = new StringBuilder();
        for(Playlist playlist : account.getPlaylists())
        {
            if(playlist.getName().equals(playlistName))
            {
                playlists.append(".").append(playlist.toString()).append("\n");
                playlists.append(playlist.getAudiosList());
            }
        }

        if(playlists.isEmpty()) {
            return "there is no such playlist";
        }
        else {
            playlists.deleteCharAt(playlists.length() - 1);
            return playlists.toString();
        }
    }
    public String showPremiumInfo()
    {
        StringBuilder premiumInfo = new StringBuilder();
        premiumInfo.append(".Monthly : ").append(PremiumPackages.MONTHLY.getDays()).append(" days | price : ").append(PremiumPackages.MONTHLY.getPrice());
        premiumInfo.append("\n.Bimonthly : ").append(PremiumPackages.BIMONTHLY.getDays()).append(" days | price : ").append(PremiumPackages.BIMONTHLY.getPrice());
        premiumInfo.append("\n.Semiyearly : ").append(PremiumPackages.SEMIYEARLY.getDays()).append(" days | price : ").append(PremiumPackages.SEMIYEARLY.getPrice());
        return premiumInfo.toString();
    }
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
    public String purchasePremium(int days) throws ParseException, NotEnoughCredit {
        if (days == 30) {
            return monthlyPremium();
        }
        if (days == 60) {
            return biMonthlyPremium();
        }
        if (days == 180) {
            return semiYearlyPremium();
        }
        return null;
    }
    public String monthlyPremium() throws NotEnoughCredit, ParseException
    {
        if (account.getAccountCredit() < PremiumPackages.MONTHLY.getPrice()) {
            throw new NotEnoughCredit("Your credit is not enough");
        }
        else
        {
            account.setAccountCredit(account.getAccountCredit() - PremiumPackages.MONTHLY.getPrice());
            if (account instanceof FreeListener)
            {
                Double credit = account.getAccountCredit();
                getDataBase().getUsers().remove(account);
                String date = account.getBirthDate().toString();
                date = date.replace('-','/');
                PremiumListener premiumListener = new PremiumListener(account.getUserName(), account.getPassword(), account.getFullName(), account.getEmail(), account.getPhoneNumber(),date);
                premiumListener.setBirthDate(account.getBirthDate());
                premiumListener.setFavouriteGenres(account.getFavouriteGenres());
                premiumListener.setLikedAudioFiles(account.getLikedAudioFiles());
                premiumListener.setPlaylists(account.getPlaylists());
                premiumListener.setFollowings(account.getFollowings());
                premiumListener.setPlayTimes(account.getPlayTimes());
                premiumListener.setAccountCredit(credit);
                getDataBase().getUsers().add(premiumListener);
                premiumListener.setLeftDays(31);
                setAccount(premiumListener);
                account.setPremiumDeadline(addDays(new java.sql.Date(new Date().getTime()),30));
                updateLeftDays();
                return "your account upgraded to premium successfully";
            }
            else if(account instanceof PremiumListener) {
                ((PremiumListener) account).setLeftDays(((PremiumListener) account).getLeftDays() + 30);
                account.setPremiumDeadline(addDays(account.getPremiumDeadline(), 30));return "Your subscription has been renewed successfully";
            }
        }
        return null;
    }
    public String biMonthlyPremium() throws ParseException, NotEnoughCredit {
        if (account.getAccountCredit() < PremiumPackages.BIMONTHLY.getPrice()) {
            throw new NotEnoughCredit("Your credit is not enough");
        } else {
            account.setAccountCredit(account.getAccountCredit() - PremiumPackages.BIMONTHLY.getPrice());
            if (account instanceof FreeListener) {
                Double credit = account.getAccountCredit();
                getDataBase().getUsers().remove(account);
                String date = account.getBirthDate().toString();
                date = date.replace('-','/');
                PremiumListener premiumListener = new PremiumListener(account.getUserName(), account.getPassword(), account.getFullName(), account.getEmail(), account.getPhoneNumber(),date);
                premiumListener.setAccountCredit(credit);
                premiumListener.setBirthDate(account.getBirthDate());
                premiumListener.setFavouriteGenres(account.getFavouriteGenres());
                premiumListener.setLikedAudioFiles(account.getLikedAudioFiles());
                premiumListener.setPlaylists(account.getPlaylists());
                premiumListener.setFollowings(account.getFollowings());
                premiumListener.setPlayTimes(account.getPlayTimes());
                getDataBase().getUsers().add(premiumListener);
                premiumListener.setLeftDays(61);
                setAccount(premiumListener);
                account.setPremiumDeadline(addDays(new java.sql.Date(new Date().getTime()),60));
                updateLeftDays();
                return "your account upgraded to premium successfully";
            }
            else if(account instanceof PremiumListener)
            {
                ((PremiumListener) account).setLeftDays(((PremiumListener) account).getLeftDays() + 60);
                account.setPremiumDeadline(addDays(account.getPremiumDeadline(),60));
                updateLeftDays();
                return "Your subscription has been renewed successfully";
            }
        }
        return null;
    }
    public String semiYearlyPremium() throws ParseException, NotEnoughCredit {
        if (account.getAccountCredit() < PremiumPackages.SEMIYEARLY.getPrice())
        {
            throw new NotEnoughCredit("Your credit is not enough");
        }
        else {
            account.setAccountCredit(account.getAccountCredit() - PremiumPackages.SEMIYEARLY.getPrice());
            if (account instanceof FreeListener) {
                Double credit = account.getAccountCredit();
                getDataBase().getUsers().remove(account);
                String date = account.getBirthDate().toString();
                date = date.replace('-', '/');
                PremiumListener premiumListener = new PremiumListener(account.getUserName(), account.getPassword(), account.getFullName(), account.getEmail(), account.getPhoneNumber(), date);
                premiumListener.setAccountCredit(credit);
                premiumListener.setBirthDate(account.getBirthDate());
                premiumListener.setFavouriteGenres(account.getFavouriteGenres());
                premiumListener.setLikedAudioFiles(account.getLikedAudioFiles());
                premiumListener.setPlaylists(account.getPlaylists());
                premiumListener.setFollowings(account.getFollowings());
                premiumListener.setPlayTimes(account.getPlayTimes());
                getDataBase().getUsers().add(premiumListener);
                premiumListener.setLeftDays(181);
                setAccount(premiumListener);
                account.setPremiumDeadline(addDays(new java.sql.Date(new Date().getTime()), 180));
                updateLeftDays();
                return "your account upgraded to premium successfully";
            } else if (account instanceof PremiumListener) {
                ((PremiumListener) account).setLeftDays(((PremiumListener) account).getLeftDays() + 180);
                account.setPremiumDeadline(addDays(account.getPremiumDeadline(), 180));
                updateLeftDays();
                return "Your subscription has been renewed successfully";
            }
        }
        return null;
    }
    public String increaseCredit(double value)
    {
        account.setAccountCredit(account.getAccountCredit() + value);
        return value + " dollar has been added to your credit successfully\ncredit: " +account.getAccountCredit();
    }
    public StringBuilder giveSuggestions(int n)
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
                    audio.setScore(audio.getScore() + 1);
                }
                for(Artist artist: account.getFollowings())
                {
                    if(artist.getUserName().equals(audio.getArtist()))
                    {
                         audio.setScore(audio.getScore() + 1);
                    }
                }
                if( account.getPlayTimes().containsKey(audio))
                {
                    audio.setScore((audio.getScore() + 1));
                }
                if(audio.getScore() != 0) {
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
        if(audios.isEmpty())
        {
            return new StringBuilder("there is no suggestion");
        }
        while(n!= 0)
        {
            audios.remove(audios.getLast());
            --n;
        }
        return arraylistToString(audios);
    }
    public String likeAudioFile(long audioId) throws LikedBefore
    {
        for(Audio audio : getDataBase ().getAudioFiles())
        {
            if(audio.getId() == audioId && !account.getLikedAudioFiles().contains(audio))
            {
                audio.setLikes(audio.getLikes() + 1);
                account.getLikedAudioFiles().add(audio);
                return "you liked " + audio.getTitle();
            }
            if(audio.getId() == audioId && account.getLikedAudioFiles().contains(audio))
            {
                throw new LikedBefore("you liked this before");
            }
        }
        return "id is invalid";
    }
    public StringBuilder arraylistToString(ArrayList<Audio> followers)
    {
        StringBuilder files = new StringBuilder();
        int count = 0;
        for(Audio audio : followers)
        {
            ++count;
            files.append(count).append(". ").append(audio.toString()).append("\n");
        }
        files.deleteCharAt(files.length() - 1);
        return files;
    }
    public String showLyric(long audioId) {
        if (findAudio(audioId) == null) {
            return "id is invalid";
        } else if (findAudio(audioId) instanceof Music) {
            return ((Music) findAudio(audioId)).getLyrics();
        } else {
            return ((Podcast) findAudio(audioId)).getCaption();
        }
    }
    public void updateLeftDays()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                ((PremiumListener)account).setLeftDays(((PremiumListener)account).getLeftDays() - 1);
                //for real;
//                if(((PremiumListener)account).getLeftDays() == 0)
//                {
//                    getDataBase().getUsers().remove(account);
//                    String date = account.getBirthDate().toString();
//                    date = date.replace('-','/');
//                    FreeListener freeListener = null;
//                    try {
//                        freeListener = new FreeListener(account.getUserName(), account.getPassword(), account.getFullName(), account.getEmail(), account.getPhoneNumber(),date);
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//                    getDataBase().getUsers().add(freeListener);
//                }
            }
        };
        timer.schedule(task,0,24 * 60 * 60000);
    }
    public StringBuilder help()
    {
        StringBuilder orders = new StringBuilder();
        orders.append(".Signup -> Signup-L-[username]-[password]-[name]-[email]-[phone number]-[birth date]\n.choose favouriteGenres -> FavouriteGenres-[favourite genres separated with comma(,)]\n.login -> Login-[username]-[password]\n.logout -> Logout\n.see accountInfo -> AccountInfo\n");
        orders.append(".GetSuggestions ->GetSuggestions-n\n.Artists -> Artists\n.Artist -> Artist-[username]\n.Follow -> Follow-[username]\n.Search -> Search-[artist name OR audio’s title]\n.Sort:\nbased on likes -> Sort-L\nbased on plays -> Sort-P\n.Filter: \nfilter by artist -> Filter-A-[Artist's username]\nfilter by Genre -> Filter-G-[selected genre]\nfilter by Date -> Filter-D-[Date in (yyyy-MM-dd) format]\nfilter between two dates -> BetweenDates[date1-date2]\n.Add playlist -> Add-[playlist’s name]-[audio’s ID]\n.ShowPlaylists -> ShowPlaylists\n.Show selected playlist information -> SelectPlaylist-[playlist’s name]\n.Play audio -> Play-[audio’s ID]\n.Like audio -> Like-[audio’s ID]\n.show Lyric/caption of an audio -> Lyric-[audio’s ID]\n.create new playlist -> NewPlaylist-[playlist’s name]\n.see followings list -> Followings\n.report an artist -> Report-[artist’s username]-[explanation]\n.increase account credit -> IncreaseCredit-[value]\n.purchase premium -> GetPremium-[package]");
        return new StringBuilder("\033[1;40;97m" + orders);
    }
    public ArrayList<Audio> sortMusicScores(ArrayList<Audio> musics)
    {
        for(int i=0;i<musics.size();++i)
        {
            int j;
            for(j=i+1;j<musics.size();++j)
            {
                if(musics.get(j).getScore() > musics.get((i)).getScore())
                {
                    Collections.swap(musics,i,j);
                }
            }
        }
        return musics;
    }
}


