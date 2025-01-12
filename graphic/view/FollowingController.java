package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.Listener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class FollowingController implements Initializable
{
    @FXML
    private Label name1;
    @FXML
    private Label type;
    @FXML
    private Circle pic;
    @FXML public Label show;
    private UserAccount account;

    public void setAccount(UserAccount account)
    {
        this.account = account;
    }
    ArrayList<Artist> followings;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image picture = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("ab676161000051746b134287e3095d2c84b7932a.jpeg")).toExternalForm());
        pic.setFill(new ImagePattern(picture));

    }

    public void setFollowing(Artist artist)
    {
        name1.setText(artist.getUserName());
        if(artist instanceof Singer)
        {
            type.setText("Singer");
        }
        else
            type.setText("Podcaster");
    }

    public void showArtist(MouseEvent event)
    {

    }
}
