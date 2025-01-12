package org.example.graphic.controller;

import org.example.graphic.model.UserAccount;
import org.example.graphic.model.interfaces.GeneralOperations;
import org.example.graphic.model.user.Admin;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.listener.Listener;

import static org.example.graphic.controller.AdminController.getAdminController;
import static org.example.graphic.controller.ArtistController.getArtistController;
import static org.example.graphic.controller.ListenerController.getListenerController;

public class LogoutController implements GeneralOperations {
    UserAccount account;

    public LogoutController(UserAccount account) {
        this.account = account;
    }
    @Override
    public String logout()
    {
        account = null;
        return "you logged out";
    }
}
