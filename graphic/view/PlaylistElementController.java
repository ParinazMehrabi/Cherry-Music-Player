package org.example.graphic.view;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.audio.Audio;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlaylistElementController implements Initializable {

    @FXML public Label title;
    @FXML public Label artist;
    @FXML public Label showPsong;
    @FXML public Label date;
    @FXML public ImageView cover;
    private List<Audio> audiosList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
    public void setAudios(Audio audio)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        date.setText(dtf.format(localDate));
        title.setText(audio.getTitle());
        artist.setText(audio.getArtist());
        Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(audio.getCover())).toExternalForm());
        cover.setImage(image);
    }

    public void showPlaylistSong(MouseEvent event) {

    }
}
