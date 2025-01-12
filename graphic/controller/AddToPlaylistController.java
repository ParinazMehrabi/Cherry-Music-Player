package org.example.graphic.controller;

import org.example.graphic.exceptions.AddedBefore;
import org.example.graphic.exceptions.AddedBefore;
import org.example.graphic.exceptions.FreeAccountLimitException;
import org.example.graphic.exceptions.NonExistedPlaylst;
import org.example.graphic.model.Playlist;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.Listener;
import org.example.graphic.model.user.listener.PremiumListener;

public class AddToPlaylistController
{
    private static AddToPlaylistController addToPlaylistController;
    private Listener account;
    public Listener getAccount()
    {
        return account;
    }
    public static AddToPlaylistController getListenerController() {
        if (addToPlaylistController == null)
            addToPlaylistController = new AddToPlaylistController();
        return addToPlaylistController;
    }
    public void setAccount(Listener account)
    {
        this.account = account;
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
                    if (ListenerController.getListenerController().findAudio(audioId) == null) {
                        return "audio id is invalid";
                    }
                    for (Playlist playlists : freeListener.getPlaylists()) {
                        for (Audio audio : playlists.getAudiosList()) {
                            if (ListenerController.getListenerController().findAudio(audioId).getTitle().equals(audio.getTitle())) {
                                throw new AddedBefore("this music has been added before");
                            }
                        }
                    }
                    playlist.getAudiosList().add(ListenerController.getListenerController().findAudio(audioId));
                    return ListenerController.getListenerController().findAudio(audioId).getTitle() + "has been added to " + playlistName + " successfully";
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
                playlist.getAudiosList().add(ListenerController.getListenerController().findAudio(audioId));
            }
        }
        return "audio has been added to " + playlistName + " successfully";
    }
}
