package org.example.graphic.model;


import org.example.graphic.model.user.artist.Artist;

public class Report
{
    private UserAccount reporter;
    private static int c = 0;
    private Artist reported;
    private String description;
    public Report(UserAccount reporter, Artist reported, String description)
    {
        this.reporter = reporter;
        this.reported = reported;
        this.description = description;
        c++;
    }

    public UserAccount getReporter() {
        return reporter;
    }

    public void setReporter(UserAccount reporter) {
        this.reporter = reporter;
    }

    public Artist getReported() {
        return reported;
    }

    public void setReported(Artist reported) {
        this.reported = reported;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return String.valueOf(c) + ". " + " report" ;
    }
}
