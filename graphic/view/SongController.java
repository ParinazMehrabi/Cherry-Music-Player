package org.example.graphic.view;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.audio.Audio;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SongController implements Initializable{
    BaseHomeController baseHomeController = new BaseHomeController();
    public javafx.scene.control.Label img2;
    public Label artist;
    @FXML
    private ImageView img;
    @FXML
    public Label play;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
    public void setInfo(Audio music)
    {
        if(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm() != null) {
            Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm());
            img.setImage(image);
            img2.setText(music.getTitle());
            artist.setText(music.getArtist());
        }
    }


    public void play(MouseEvent event)
    {
        baseHomeController.s_ll.setMaxSize(600,400);
        baseHomeController.sc1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        BoxBlur blur = new BoxBlur();
        TranslateTransition slide = new TranslateTransition();
        baseHomeController.s_ll.setVisible(true);
        slide.setDuration(Duration.seconds(0.7));
        baseHomeController.s_ll.setMinSize(500,300);
        slide.setNode(baseHomeController.s_ll);
        slide.setToY(-850);
        slide.play();
    }

    public void music(MouseEvent event)
    {

    }
}
