package org.example.graphic.view;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.example.graphic.BaseHomeController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable
{
    private static BaseHomeController baseHomeController;

    public HomeController()
    {
    }

    @FXML private Label bio;

    @FXML private TextField bio2;

    @FXML private Circle box;

    @FXML private Circle country;

    @FXML private Label error;

    @FXML private Circle hiphop;

    @FXML private Circle history;

    @FXML private VBox info;

    @FXML private Circle interview;

    @FXML private ImageView invisible;

    @FXML private Circle jazz;

    @FXML private Circle listener1;

    @FXML private HBox logo2;
    @FXML private AnchorPane main;

    @FXML private HBox pas;

    @FXML private PasswordField pas1;

    @FXML private TextField pass;
    @FXML private Circle podcaster1;

    @FXML private Circle pop;

    @FXML private Circle rock;

    @FXML private HBox sBox1;

    @FXML
    private AnchorPane s_ll;

    @FXML
    private AnchorPane s_ll2;

    @FXML
    private ScrollPane sc1;

    @FXML
    private Label signup2;

    @FXML
    private Circle singer1;

    @FXML
    private Circle society;

    @FXML
    private VBox top;

    @FXML
    private HBox topArtists;

    @FXML
    private Label topLabel;

    @FXML
    private HBox topMusics;

    @FXML
    private Circle truecrime;

    @FXML
    private AnchorPane type;

    @FXML
    private HBox user1;

    @FXML
    private HBox user2;

    @FXML
    private ImageView visible;

    @FXML
    private HBox vpas;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Image rockPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("rock.jpeg")).toExternalForm());
        rock.setFill(new ImagePattern(rockPic));
        Image popPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("raphael-lovaski-RjD01Is-KnI-unsplash.jpg")).toExternalForm());
        Image jazzPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("artworks-000102693494-wnuvlx-t500x500.jpg")).toExternalForm());
        pop.setFill(new ImagePattern(popPic));
        jazz.setFill(new ImagePattern(jazzPic));
        type.setVisible(true);
    }
    @FXML
    void complete(MouseEvent event)
    {
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(2));
        slide1.setNode(sc1);
        slide1.setToY(2000);
        slide1.play();
        baseHomeController.unBlur();
    }

    @FXML
    void invisible(MouseEvent event) {

    }

    @FXML
    void listener(MouseEvent event) {

    }

    @FXML
    void loginfix(MouseEvent event) {

    }

    @FXML
    void podcaster(MouseEvent event) {

    }

    @FXML
    void sbox1(MouseEvent event) {

    }

    @FXML
    void singer(MouseEvent event) {

    }

    @FXML
    void visible(MouseEvent event) {

    }

    public void signUp()
    {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(1));
        slide.setNode(type);
        slide.setToY(-500);
        slide.play();
        type.setEffect(null);
    }

}
