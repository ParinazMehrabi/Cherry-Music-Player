package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.example.graphic.model.Playlist;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable
{
    public javafx.scene.control.Label title1;
    public javafx.scene.control.Label cu;
    @FXML public Button showPInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setPlaylists(Playlist playlist)
    {
        cu.setText("by" + playlist.getCurator());
        title1.setText(playlist.getName());
    }

    public void innerPlaylistShow(MouseEvent event)
    {

    }
}
