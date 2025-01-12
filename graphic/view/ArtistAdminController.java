package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.PremiumListener;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArtistAdminController implements Initializable {

    @FXML private VBox artistList;
    @FXML private Label typeL;
    @FXML private Circle ar;
    @FXML private Label userL;
    public boolean user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user =false;
    }
    public void set(UserAccount artist)
    {
        if(!user)
        {
        Image artistPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("ab676161000051746b134287e3095d2c84b7932a.jpeg")).toExternalForm());
        ar.setFill(new ImagePattern(artistPic));
        }else
        {
        Image artistPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("output-onlinepngtools.png")).toExternalForm());
        ar.setFill(new ImagePattern(artistPic));
        }
        if(artist instanceof Singer)
            typeL.setText("singer");
        else if(artist instanceof FreeListener)
            typeL.setText("free listener");else if(artist instanceof PremiumListener)
            typeL.setText("premium listener");
            else
                typeL.setText("podcaster");
        userL.setText(artist.getUserName());
    }
}
