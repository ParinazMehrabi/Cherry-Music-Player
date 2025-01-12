package org.example.graphic.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.graphic.model.audio.Audio;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminChartController implements Initializable
{

    public static int c = 1;
    @FXML private Label mT;
    @FXML private Label likeC;
    @FXML private Label playC;
    @FXML private Label artistN;
    @FXML private Label count;
    public boolean t = true;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!t)
        {
            c = 1;
        }
    }
    public void setAudios(Audio audio)
    {
        if(t) {
            if (c == 11)
                c = 1;
        }
        count.setText(String.valueOf(c++) + ".");
        mT.setText("            " + audio.getTitle());
        likeC.setText(String.valueOf(audio.getLikes()));
        playC.setText(String.valueOf(audio.getPlays()));
        artistN.setText(audio.getArtist());
    }
}
