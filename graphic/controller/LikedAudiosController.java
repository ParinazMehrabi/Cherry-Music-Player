package org.example.graphic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.audio.Music;
import org.example.graphic.model.user.listener.Listener;
import org.example.graphic.view.SongController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.example.graphic.HelloApplication.topMusic;

public class LikedAudiosController
{
    public void ShowLikedSongs(UserAccount account, Label title, ImageView imgv, Label artistv, Label lyrics, ImageView coverFlow, HBox topMusics)
    {
        try {
            for (Audio mostLikedSong :topMusic ) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setInfo(mostLikedSong);
                title.setText(mostLikedSong.getTitle());
                songController.play.setOnMouseClicked(event ->
                {
                    title.setText(mostLikedSong.getTitle());
                    imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                    artistv.setText(mostLikedSong.getArtist());
                    Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                    lyrics.setText(((Music) mostLikedSong).getLyrics());
                    coverFlow.setImage(pause5);
                });
                topMusics.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
