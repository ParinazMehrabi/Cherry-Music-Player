package org.example.graphic.controller;


import org.example.graphic.model.Report;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.user.Admin;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;

import java.util.ArrayList;

import static java.lang.String.valueOf;
import static org.example.graphic.model.DataBase.getDataBase;

public class AdminController
{
    private static AdminController adminController;
    private Admin admin;
    private AdminController() {
    }
    public static AdminController getAdminController() {
        if (adminController == null)
            adminController = new AdminController();
        return adminController;
    }

    public Admin getAdmin()
    {
        return this.admin;
    }

    public void setAdmin(Admin admin)
    {
        this.admin = admin;
    }
    public void login(Admin admin)
    {
        setAdmin(admin);
    }
    //statistics
    public ArrayList<Audio> popularAudioFiles()
    {
        return ListenerController.getListenerController().sortAudioFiles('L');
    }
    public String artists() {
        StringBuilder singers = new StringBuilder();
        StringBuilder podcasters = new StringBuilder();
        for(UserAccount userAccount : getDataBase().getUsers())
        {
            if(userAccount instanceof Singer)
            {
                singers.append(".").append((userAccount.toString())).append("\n");
            }
            if(userAccount instanceof Podcaster)
            {
                podcasters.append(".").append(userAccount.toString()).append("\n");
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
    public String showSelectedArtist(String userName)
    {
        for(UserAccount artist : getDataBase().getUsers())
        {
            if(artist.getUserName().equals(userName))
            {
                return "\033[1;40;97m" + artist.toString();
            }
        }
        return  "\033[1;40;31m" + "id is invalid";
    }
    public String audioFiles() {
        StringBuilder list = new StringBuilder();
        for (Audio audio : getDataBase().getAudioFiles()) {
            list.append(".").append(audio.toString()).append("\n");
        }
        if(list.isEmpty())
        {
            return "\033[1;40;31m" + "there list is empty";
        }
        list.deleteCharAt(list.length() - 1);
        return "\033[1;40;97m" + list.toString();
    }
    public String showSelectedAudio(long id)
    {
        for (Audio audio : getDataBase().getAudioFiles())
        {
            if(audio.getId() == id)
            {
                return audio.toString();
            }
        }
        return "\033[1;40;31m" + "id is invalid";
    }
    public StringBuilder showReports() {
        StringBuilder reports = new StringBuilder();
        for (Report report : getDataBase().getReports()) {
            reports.append(".").append(report).append("\n");
        }
        if(reports.isEmpty())
        {
            return new StringBuilder("\033[1;40;31m" + new StringBuilder("there is no report yet"));
        }
        reports.deleteCharAt(reports.length() - 1);
        return reports;
    }
    public StringBuilder help()
    {
        StringBuilder orders = new StringBuilder();
        orders.append(".login -> Login-[username]-[password]\n.logout -> Logout\n.see accountInfo -> AccountInfo\n");
        orders.append(".audio files sorted by likes -> Statistics\n.see all audio files -> Audios\n.see selected audio file -> Audio-[audio’s ID]\n.see all artists -> Artists\n.see selected artist -> Artist-[username]\n.see all reports -> Reports -[username]");
        return orders;
    }
    public String showAccInfo()
    {
        return admin.toString();
    }
}
