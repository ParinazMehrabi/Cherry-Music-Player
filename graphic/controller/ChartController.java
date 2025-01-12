package org.example.graphic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.example.graphic.BaseHomeController;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.user.Admin;
import org.example.graphic.view.AdminChartController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChartController
{
    private static ChartController chartController;
    private Admin admin;
    private ChartController() {
    }
    public static ChartController getChartController() {
        if (chartController == null)
            chartController = new ChartController();
        return chartController;
    }
    public void mostPSet() {
        List<Audio> audioList = new ArrayList<>(ListenerController.getListenerController().sortAudioFiles('L'));
        while(audioList.size() != 10)
        {
            audioList.remove(audioList.getLast());
        }
        for (Audio audio : audioList) {
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(getClass().getResource("AdminChart.fxml"));
                HBox hBox = fxmlLoader2.load();
                AdminChartController adminChartController = fxmlLoader2.getController();
                adminChartController.setAudios(audio);
                BaseHomeController.getBaseHomeController().getMost().getChildren().add(hBox);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
