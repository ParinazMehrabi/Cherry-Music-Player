package org.example.graphic.model;

import org.example.graphic.model.audio.Audio;

import java.util.ArrayList;

public class DataBase
{
    private static DataBase dataBase;
    private ArrayList<UserAccount> users;
    private ArrayList<Audio> audioFiles;
    private ArrayList<Report> reports;
    private DataBase()
    {
        this.users = new ArrayList<>();
        this.audioFiles = new ArrayList<>();
        this.reports = new ArrayList<>();
    }
    public static DataBase getDataBase()
    {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }
    public ArrayList<UserAccount> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserAccount> users) {
        this.users = users;
    }

    public ArrayList<Audio> getAudioFiles() {
        return audioFiles;
    }

    public void setAudioFiles(ArrayList<Audio> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
}
