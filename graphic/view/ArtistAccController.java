package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Singer;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArtistAccController implements Initializable {
    @FXML public Label showInfo;
    @FXML private Label artistUsername;
    @FXML private Label type;
    @FXML Circle artist;

    private Artist mainArtist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image artistPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("ab676161000051746b134287e3095d2c84b7932a.jpeg")).toExternalForm());
        artist.setFill(new ImagePattern(artistPic));
    }
    public void setData(Artist artist)
    {
        this.mainArtist = artist;
        artistUsername.setText(mainArtist.getUserName());
        if(mainArtist instanceof Singer)
        {
            type.setText("Singer");
        }
        else
            type.setText("Podcaster");
    }

    public void info(MouseEvent event)
    {
    }
}
