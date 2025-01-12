package org.example.graphic.model.audio;

public class Music extends Audio
{
    private String lyrics;
    public Music(String title, String artist,String genre, String link, String cover, String lyrics)
    {
        super(title, artist,genre, link, cover);
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString()
    {
        return super.toString() + "\n lyrics : \n" + lyrics;
    }
}
