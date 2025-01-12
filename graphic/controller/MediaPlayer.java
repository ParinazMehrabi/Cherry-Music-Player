package org.example.graphic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.audio.Music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static org.example.graphic.HelloApplication.topMusic;

public class MediaPlayer
{
    private Circle pause2;
    private Music musics;
    private Label title;
    private Label artistv;
    private Media media;
    private ImageView imgv;
    private javafx.scene.media.MediaPlayer mediaPlayer;
    private ArrayList<Music> topMusic= new ArrayList<>();
    private int count = 1;

    public MediaPlayer(Media media) {
        this.media = media;
    }

    public MediaPlayer(Circle pause2, Music musics, Label title, Label artistv, Media media, ImageView imgv, javafx.scene.media.MediaPlayer mediaPlayer, ArrayList<Music> topMusic, ProgressBar songPBar) {
        this.pause2 = pause2;
        this.musics = musics;
        this.title = title;
        this.artistv = artistv;
        this.media = media;
        this.imgv = imgv;
        this.mediaPlayer = mediaPlayer;
        this.topMusic = topMusic;
    }

    public void play_pause(Circle pause2,Label title) throws FileNotFoundException {
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream("/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/src/main/resources/org/example/graphic/109197.png");
        if(pause2 != null)
            pause2.setFill(new ImagePattern(new Image(fileInputStream)));
        if (count % 2 != 0) {
            play(pause2,title,songPBar);
        } else
            pause();
    }
    public void play(Circle pause2,Label title,ProgressBar songPBar) throws FileNotFoundException {
        beginTimer(songPBar);
        if (count == 1) {
            musics = topMusic.get(0);
            title.setText(musics.getTitle());
            media = new Media(musics.getLink());
            mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        }
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream("/Users/parinaz/Downloads/javafx-sdk-22/demo18/src/main/resources/images-2.jpeg");
        pause2.setFill(new ImagePattern(new Image(fileInputStream)));
        mediaPlayer.play();
        ++count;
    }
    public void pause() {
        cancelTimer();
        mediaPlayer.pause();
        ++count;
    }
    public void last(ImageView imgv,Label artistv,Label title) throws FileNotFoundException {
        if (topMusic.indexOf(musics) == 0) {
            if(running)
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.toArray().length - 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
        } else {
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.indexOf(musics) - 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
        }
        pause();
        title.setText(musics.getTitle());
        artistv.setText(musics.getArtist());
        media = new Media(musics.getLink());
        mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.play();

    }

    public void next(ImageView imgv,Label artistv,Music musics,Label title) throws FileNotFoundException {
        if (topMusic.indexOf(musics) != topMusic.size() - 1) {
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.indexOf(musics) + 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
            artistv.setText(topMusic.get(topMusic.indexOf(musics)).getArtist());
        } else {
            {
                cancelTimer();
            }
            musics = topMusic.getFirst();
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
        }
        pause();
        title.setText(musics.getTitle());
        media = new Media(musics.getLink());
        mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.play();
    }
    Timer timer;
    TimerTask task;
    boolean running;
    @FXML private ProgressBar songPBar = new ProgressBar();
    public void beginTimer(ProgressBar songPBar)
    {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songPBar.setProgress(current/end);
                if(current/end == 1)
                {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);

    }
    public void cancelTimer()
    {
        running = false;
        timer.cancel();
    }
        public void change(Slider vSlider)
    {
        mediaPlayer.setVolume(vSlider.getValue() * 0.01);
    }
}
