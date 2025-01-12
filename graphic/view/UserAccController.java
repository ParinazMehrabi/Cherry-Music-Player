package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.Listener;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserAccController implements Initializable {
    @FXML public Label showInfo;
    @FXML private Label artistUsername;
    @FXML private Label type;
    @FXML Circle artist;
    private Listener mainArtist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Image artistPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("output-onlinepngtools.png")).toExternalForm());
            artist.setFill(new ImagePattern(artistPic));

    }
    public void setData(Listener artist)
    {
        this.mainArtist = artist;
        artistUsername.setText(mainArtist.getUserName());
        if(mainArtist instanceof FreeListener)
        {
            type.setText("free");
        }
        else
            type.setText("premium");
    }

    public void info(MouseEvent event)
    {
    }
}
