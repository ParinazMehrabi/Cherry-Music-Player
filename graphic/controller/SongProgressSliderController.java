package org.example.graphic.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class SongProgressSliderController {

    private final Media media;
    private final MediaPlayer mediaPlayer;
    private final Slider progressSlider;

    public SongProgressSliderController(String filePath) {
        this.media = new Media(filePath);
        this.mediaPlayer = new MediaPlayer(media);
        this.progressSlider = new Slider();

        // Set slider properties (min, max, etc.)
        progressSlider.setMin(0.0);
        progressSlider.setMax(mediaPlayer.getTotalDuration().toMillis());

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                double progress = newValue.toMillis() / mediaPlayer.getTotalDuration().toMillis();
                String color = calculateColor(progress); // Function to define color based on progress
                progressSlider.lookup(".slider .track").setStyle("-fx-fill: " + color + ";");
            }
        });
    }

    public void playSong() {
        mediaPlayer.play();
    }

    private String calculateColor(double progress) {
        // Implement logic to define the color based on progress (e.g., gradient based on progress)
        // This example uses a simple progress-based color change
        int red = (int) (progress * 255);
        int green = 255 - red;
        return String.format("#%02x%02x%02x", red, green, 0);
    }
}