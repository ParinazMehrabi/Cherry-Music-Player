package org.example.graphic.model.audio;

public class Podcast extends Audio
{
    private String caption;
    public Podcast(String title, String artist, String genre, String link, String cover, String caption)
    {
        super(title,artist, genre, link, cover);
        this.caption = caption;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    @Override
    public String toString()
    {
        return super.toString() + "\n caption : \n" + caption;
    }
}
