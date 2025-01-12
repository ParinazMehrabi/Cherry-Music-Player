package org.example.graphic;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import static org.example.graphic.HelloApplication.suggestions;
import static org.example.graphic.HelloApplication.topMusic;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.graphic.controller.*;
import org.example.graphic.exceptions.*;
import org.example.graphic.model.*;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.audio.Music;
import org.example.graphic.model.audio.Podcast;
import org.example.graphic.model.user.Admin;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.Listener;
import org.example.graphic.view.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static org.example.graphic.model.DataBase.getDataBase;

public class BaseHomeController implements Initializable {
    public AnchorPane repp1;
    @FXML
    private Label topLabel;
    @FXML
    private HBox audiosSc;
    @FXML private Slider vSlider;
    @FXML private ProgressBar pro;
    private ArrayList<Node> allPanes = new ArrayList<>();
    private Stack<Node> pages = new Stack<>();
    private Audio musics;
    private Media media;
    private int count = 1;
    private MediaPlayer mediaPlayer;
    private ArrayList<Audio> ls;

    private UserAccount account;
    public static BaseHomeController baseHomeController;

    public static BaseHomeController getBaseHomeController() {
        if (baseHomeController == null)
            baseHomeController = new BaseHomeController();
        return baseHomeController;
    }

    @FXML
    void play_pause(MouseEvent event) throws FileNotFoundException {
        if (count % 2 != 0) {
            play();
        } else
            pause();
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration totalDuration = mediaPlayer.getCycleDuration();
            Duration remainingTime = totalDuration.subtract(currentTime);
            currentTimeLabel.setText(formatTime(currentTime));
            remainingTimeLabel.setText("-" + formatTime(remainingTime));
            mediaPlayer.setOnEndOfMedia(() ->
            {
                try {
                    next(event);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }
    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
    void play() throws FileNotFoundException {
        beginTimer();
        if (count == 1) {
            musics = topMusic.get(0);
            title.setText(musics.getTitle());
            media = new Media(musics.getLink());
            mediaPlayer = new MediaPlayer(media);
        }
        FileInputStream fileInputStream;
        fileInputStream = new FileInputStream("/Users/parinaz/Downloads/javafx-sdk-22/demo18/src/main/resources/images-2.jpeg");
        pause2.setFill(new ImagePattern(new Image(fileInputStream)));
        mediaPlayer.play();
        ++count;
        pro.setOnMouseClicked((MouseEvent e) -> {
            double mouseX = e.getX();
            double progressbarWidth = pro.getWidth();
            double normalizedPosition = mouseX / progressbarWidth;
            double seekPosition = normalizedPosition * mediaPlayer.getCycleDuration().toSeconds();
            mediaPlayer.seek(Duration.seconds(seekPosition));
            pro.setProgress(normalizedPosition);
        });
    }
    void pause() throws FileNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/src/main/resources/org/example/graphic/images-5.jpg");
        pause2.setFill(new ImagePattern(new Image(fileInputStream)));
        cancelTimer();
        mediaPlayer.pause();
        ++count;
    }

    @FXML
    public void last(MouseEvent event) throws FileNotFoundException {
        like.setVisible(true);
        liked.setVisible(false);
        if (topMusic.indexOf(musics) == 0) {
            if(running)
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.toArray().length - 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
        } else {
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.indexOf(musics) - 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
        }
        pause();
        title.setText(musics.getTitle());
        artistv.setText(musics.getArtist());
        media = new Media(musics.getLink());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }

    @FXML
    void next(MouseEvent event) throws FileNotFoundException {
        like.setVisible(true);
        liked.setVisible(false);
        if (topMusic.indexOf(musics) != topMusic.size() - 1) {
            {
                cancelTimer();
            }
            musics = topMusic.get(topMusic.indexOf(musics) + 1);
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
            artistv.setText(topMusic.get(topMusic.indexOf(musics)).getArtist());
            if(topMusic.get(topMusic.indexOf(musics)) instanceof Music)
            {
                lyrics.setText(((Music) topMusic.get(topMusic.indexOf(musics))).getLyrics());
            }
            if(topMusic.get(topMusic.indexOf(musics)) instanceof Podcast)
            {
                lyrics.setText(((Podcast) topMusic.get(topMusic.indexOf(musics))).getCaption());
            }
            coverFlow.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));;
        } else {
            {
                cancelTimer();
            }
            musics = topMusic.getFirst();
            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
            if(topMusic.get(topMusic.indexOf(musics)) instanceof Music)
            {
                lyrics.setText(((Music) topMusic.get(topMusic.indexOf(musics))).getLyrics());
            }
            if(topMusic.get(topMusic.indexOf(musics)) instanceof Podcast)
            {
                lyrics.setText(((Podcast) topMusic.get(topMusic.indexOf(musics))).getCaption());
            }
            coverFlow.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));

        }
        pause();
        title.setText(musics.getTitle());
        media = new Media(musics.getLink());
        mediaPlayer = new MediaPlayer(media);
        liked.setVisible(false);
        like.setVisible(true);
        mediaPlayer.play();
        //mediaplayer2.next
    }

    @FXML
    private Circle previous;
    @FXML
    private Circle previous2;
    @FXML
    private TextField tf1;
    @FXML
    private Circle userPic;
    @FXML
    private HBox user;
    @FXML
    private AnchorPane type;
    @FXML
    public AnchorPane s_ll;
    @FXML
    private HBox pas;
    @FXML
    private HBox vpas;
    @FXML
    private VBox info;
    @FXML
    private HBox p;
    @FXML
    private VBox library;
    @FXML
    private AnchorPane anc;
    @FXML
    private VBox slider;
    private Text signup = new Text();
    private Text login = new Text();
    @FXML
    private HBox sBox;
    @FXML
    private HBox h1;
    @FXML
    private HBox h2;
    @FXML
    private HBox h3;
    @FXML
    private HBox h5;
    @FXML
    private HBox h6;
    @FXML
    private PasswordField pas1;
    @FXML
    private TextField log;
    @FXML
    private TextField pass;
    @FXML
    public ScrollPane sc1;
    @FXML
    private VBox mainV;
    @FXML
    private AnchorPane playlistName;
    @FXML
    private Circle listener1;
    @FXML
    private Circle singer1;
    @FXML
    private Circle pop;
    @FXML
    private Circle rock;
    @FXML
    private Circle jazz;
    @FXML
    private Circle hiphop;
    @FXML
    private Circle country;
    @FXML
    private Circle truecrime;
    @FXML
    private Circle society;
    @FXML
    private Circle interview;
    @FXML
    private Circle history;
    @FXML
    private Circle podcaster1;
    @FXML
    private Label bio;
    @FXML
    private TextField bio2;
    @FXML
    private Circle box;
    @FXML
    private Label numberE;
    @FXML
    private HBox playlist;
    @FXML
    private Label lyrics;
    @FXML
    private ImageView coverFlow;
    @FXML
    private HBox topMusics;
    @FXML
    private HBox logo2;
    @FXML
    private ScrollPane mainScroll;
    @FXML
    private VBox top;
    @FXML
    private Circle pause2;
    @FXML
    private Label title;
    @FXML private Label repp;
    @FXML
    private Label artistv;
    @FXML
    private ImageView imgv;
    @FXML
    private Circle userprofile;
    @FXML
    private Circle userprofile1;
    @FXML
    private AnchorPane listenerP;
    @FXML
    private AnchorPane mainAnc;
    @FXML
    private AnchorPane pInfo;
    @FXML
    private ScrollPane sc;
    @FXML
    private ScrollPane lyrics2;

    private boolean podcasterAcc = false;
    @FXML
    private Label overview;
    @FXML
    private Label usernameE;
    @FXML
    private Label emailE;
    @FXML
    private Label fullnameE;
    @FXML private Label des;
    @FXML private Label from;
    @FXML private Label to;
    @FXML
    private AnchorPane main;
    private ArrayList<Playlist> playlists;
    @FXML
    private Circle profileArtist;
    @FXML
    private Button bt1;
    @FXML
    private Label l1;
    @FXML
    private Label about;
    @FXML
    private TextField fullname;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField number;
    @FXML
    private Label passwordE;
    @FXML
    private Label passInE;
    @FXML private ScrollPane scrollUsers;
    @FXML private GridPane gridUsers;
    @FXML private HBox scA1;
    @FXML
    private Label usernameInE;
    @FXML
    private Label birthdateE;
    @FXML
    private AnchorPane genres;

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    private MenuItem menuItem = new MenuItem("Logout");
    private MenuButton menuButton = new MenuButton(null, null, menuItem);
    private ArrayList<Audio> mostLikedSongs;
    private ArrayList<Artist> followings;

    @FXML
    private HBox following;
    @FXML
    private VBox playlistMusics;
    @FXML
    private HBox lastRelease;
    @FXML private HBox lastRelease1;
    @FXML private HBox lastRelease21;
    @FXML
    private GridPane gridArtists;
    @FXML
    private GridPane gridAudios;
    @FXML
    private AnchorPane r1;
    @FXML
    private AnchorPane hip;
    @FXML private Label pd;
    @FXML
    private AnchorPane p1;
    @FXML
    private AnchorPane j1;
    @FXML
    private ScrollPane premium;
    @FXML
    private AnchorPane t1;
    @FXML
    private AnchorPane c1;
    @FXML VBox artistList;
    @FXML
    private AnchorPane soci1;
    @FXML
    private AnchorPane inter;
    @FXML
    private AnchorPane histor;
    @FXML
    private ScrollPane sc2;
    @FXML
    private Label followers;
    @FXML private Label LC;
    @FXML private Label AC;
    @FXML private Label PC;
    @FXML private AreaChart<String,Number> analystics;
    private boolean r1t = false;
    private boolean hipt = false;
    private boolean p1t = false;
    private boolean j1t = false;
    private boolean t1t = false;
    private boolean c1t = false;
    private boolean soci1t = false;
    private boolean intert = false;
    private boolean histort = false;
    private List<Artist> artists = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();

    public void home(MouseEvent event) {
        mainScroll.setVisible(true);
        pages.add(mainScroll);
    }

    @FXML
    private Label usernameArtist;
    @FXML
    private Label typeProf;
    @FXML
    private Label biography;
    @FXML
    private VBox mostP_L;
    @FXML private MenuButton sort;
    public VBox  getMost()
    {
        return mostP_L;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages.add(mainScroll);
        allPanes.add(mainScroll);

        try {
            SignupController.getSignupController().signup('L', "thprnz", "124", "singer", "singer@gmail.com", "09123456789", "12/12/1990", null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        mostPSet('L');
        p();
        ObservableList<Audio> data = FXCollections.observableArrayList(DataBase.getDataBase().getAudioFiles());
        ObservableList<Audio> filteredData = FXCollections.observableArrayList();
        tf1.textProperty().addListener((observable, oldValue, newValue) ->
        {
            results.getChildren().clear();
            for(Audio audio : DataBase.getDataBase().getAudioFiles())
            {
                if(audio.getTitle().toLowerCase().contains(newValue) || audio.getTitle().toUpperCase().contains(newValue))
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                    VBox vBox = null;
                    try {
                        vBox = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    SongController songController = fxmlLoader.getController();
                    songController.setInfo((Audio) audio);
                    title.setText(((Audio) audio).getTitle());
                    songController.play.setOnMouseClicked(event2 ->
                    {
                        ((Audio) audio).setPlays(((Audio) audio).getPlays() + 1);
                        title.setText(((Audio) audio).getTitle());
                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(((Audio) audio).getCover())).toExternalForm()));
                        artistv.setText(((Audio) audio).getArtist());
                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(((Audio) audio).getCover())).toExternalForm());
                        lyrics.setText(((Music) audio).getLyrics());
                        coverFlow.setImage(pause5);
                        if(mediaPlayer != null)
                            mediaPlayer.stop();
                        media = new Media(audio.getLink());
                        mediaPlayer = new MediaPlayer(media);
                        try {
                            play_pause(event2);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    });
                    songController.setInfo((Audio) audio);
                    title.setText(((Audio) audio).getTitle());
                    results.getChildren().add(vBox);
                }
            }
                for(UserAccount account1 : DataBase.getDataBase().getUsers())
                {
                    if(account1 instanceof Artist &&(account1.getUserName().toLowerCase().contains(newValue) ||account1.getUserName().toUpperCase().contains(newValue)))
                    {
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("artistAcc.fxml"));
                        AnchorPane vBox = null;
                        try {
                            vBox = fxmlLoader2.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ArtistAccController artistAccController = fxmlLoader2.getController();
                        artistAccController.showInfo.setOnMouseClicked(event2 ->
                        {
                            followers.setText(String.valueOf(((Artist) account1).getFollowers().size()));
                            biography.setText(((Artist) account1).getBiography());
                            if (account1 instanceof Singer) {
                                typeProf.setText("Singer");
                            } else {
                                typeProf.setText("Posdcaster");
                            }
                            usernameArtist.setText(((Artist) account1).getUserName());
                            ArrayList<Audio> musics = new ArrayList<>(((Artist) account1).getReleases());
                            allPanes.add(sc2);
                            pages.add(sc2);
                            sc2.setVisible(true);
                            try {
                                for (Audio music : musics) {
                                    FXMLLoader fxmlLoader3 = new FXMLLoader();
                                    fxmlLoader3.setLocation(getClass().getResource("song.fxml"));
                                    VBox vBox2 = fxmlLoader3.load();
                                    SongController songController = fxmlLoader3.getController();
                                    songController.setInfo(music);
                                    title.setText(music.getTitle());
                                    songController.play.setOnMouseClicked(event3 ->
                                    {
                                        music.setPlays(music.getPlays() + 1);
                                        title.setText(music.getTitle());
                                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm()));
                                        artistv.setText(music.getArtist());
                                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm());
                                        lyrics.setText(((Music) music).getLyrics());
                                        coverFlow.setImage(pause5);
                                        if(mediaPlayer != null)
                                            mediaPlayer.stop();
                                        media = new Media(music.getLink());
                                        mediaPlayer = new MediaPlayer(media);
                                        try {
                                            play_pause(event3);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    lastRelease.getChildren().add(vBox2);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        title.setText(((Artist) account1).getUserName());
                        results.getChildren().add(vBox);
                    }
                }
        });
        sc6.setVisible(false);
        Image userPic2 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("output-onlinepngtools.png")).toExternalForm());
        pers.setVisible(false);
        userprofile.setFill(new ImagePattern(userPic2));
        userprofile.setStyle("-fx-background-color: #474b4d");
        userprofile1.setFill(new ImagePattern(userPic2));
        userprofile1.setStyle("-fx-background-color: #474b4d");
        Image pause = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("pause.jpg")).toExternalForm());
        pause2.setFill(new ImagePattern(pause));
        int column = 0;
        int row = 0;
        try {
            for (Audio mostLikedSong : getDataBase().getAudioFiles()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                VBox anchorPane = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setInfo(mostLikedSong);
                songController.play.setOnMouseClicked(event -> {
                    mostLikedSong.setPlays(mostLikedSong.getPlays() + 1);
                    title.setText(mostLikedSong.getTitle());
                    imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                    artistv.setText(mostLikedSong.getArtist());
                    Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                    lyrics.setText(((Music) mostLikedSong).getLyrics());
                    coverFlow.setImage(pause5);
                    if(mediaPlayer != null)
                        mediaPlayer.stop();
                    media = new Media(mostLikedSong.getLink());
                    mediaPlayer = new MediaPlayer(media);
                    try {
                        play_pause(event);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                if (column == 8) {
                    column = 0;
                    row++;
                }
                GridPane.setConstraints(anchorPane, column++, row);
                gridAudios.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(0, 20, 20, 20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mostLikedSongs = new ArrayList<>(getLikedMus());
        if(account == null) {
            try {
                for (Audio mostLikedSong : topMusic) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                    VBox vBox = fxmlLoader.load();
                    SongController songController = fxmlLoader.getController();
                    songController.setInfo(mostLikedSong);
                    title.setText(mostLikedSong.getTitle());
                    songController.play.setOnMouseClicked(event ->
                    {
                        if(mediaPlayer != null)
                            mediaPlayer.stop();
                        media = new Media(mostLikedSong.getLink());
                        mediaPlayer = new MediaPlayer(media);
                        try {
                            play_pause(event);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        mostLikedSong.setPlays(mostLikedSong.getPlays() + 1);
                        title.setText(mostLikedSong.getTitle());
                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                        artistv.setText(mostLikedSong.getArtist());
                        musics = mostLikedSong;
                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                        if(mostLikedSong instanceof Music)
                            lyrics.setText(((Music) mostLikedSong).getLyrics());
                        else
                                lyrics.setText(((Podcast) mostLikedSong).getCaption());
                        coverFlow.setImage(pause5);
                    });
                    topMusics.getChildren().add(vBox);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Image picture = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("ab676161000051746b134287e3095d2c84b7932a.jpeg")).toExternalForm());
        userprofile11.setFill(new ImagePattern(picture));
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.7));
        slide.setToY(-850);
        slide.play();
        adminP.getChildren().remove(repos);
        Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("Image 2.jpg")).toExternalForm());
        Image image2 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("Image 5.jpg")).toExternalForm());
        Image rockim = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("rock.jpeg")).toExternalForm());
        rock.setFill(new ImagePattern(rockim));
        previous.setFill(new ImagePattern(image));
        previous2.setFill(new ImagePattern(image2));
        Image image3 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("IMG_9087.JPG")).toExternalForm());
        Image image4 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-111.jpeg")).toExternalForm());
        Image image5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-25.jpeg")).toExternalForm());
        Image image6 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-22.jpeg")).toExternalForm());
        Image popImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("pop.jpeg")).toExternalForm());
        Image artistPic = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("ab676161000051746b134287e3095d2c84b7932a.jpeg")).toExternalForm());
        Image jazzImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("jazz.jpeg")).toExternalForm());
        Image hiphopImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("indiesamples.jpg")).toExternalForm());
        Image countryImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("country.jpeg")).toExternalForm());
        Image truecrimeImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("truecrime.jpeg")).toExternalForm());
        Image societyImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("artworks-000102693494-wnuvlx-t500x500.jpg")).toExternalForm());
        Image interviewImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("61ZTbH+iGgL._UF1000,1000_QL80_.jpg")).toExternalForm());
        Image historyImg = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("history.jpeg")).toExternalForm());
        profileArtist.setFill(new ImagePattern(artistPic));
        user.getChildren().remove(userPic);
        sugg.setVisible(false);
        pop.setFill(new ImagePattern(popImg));
        jazz.setFill(new ImagePattern(jazzImg));
        hiphop.setFill(new ImagePattern(hiphopImg));
        country.setFill(new ImagePattern(countryImg));
        truecrime.setFill(new ImagePattern(truecrimeImg));
        society.setFill(new ImagePattern(societyImg));
        interview.setFill(new ImagePattern(interviewImg));
        history.setFill(new ImagePattern(historyImg));
        userPic.setFill(new ImagePattern(image3));
        listener1.setFill(new ImagePattern(image5));
        singer1.setFill(new ImagePattern(image4));
        podcaster1.setFill(new ImagePattern(image6));
        user.getChildren().add(menuButton);
        menuButton.setStyle("-fx-background-color: black");
        menuButton.setAlignment(Pos.CENTER_RIGHT);
        menuButton.setLayoutY(25);
        choice.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                genreList = FavGenres.getFavGenres().setGenres(r1t, p1t, j1t, t1t, hipt, c1t, soci1t, intert, histort);
                if (genreList.size() > 4) {
                    genreE.setVisible(true);
                    System.out.println(((Listener)account).getFavouriteGenres());
                }
                else {
                    genreE.setVisible(false);
                    TranslateTransition slide = new TranslateTransition();
                    slide.setDuration(Duration.seconds(1));
                    slide.setNode(genres);
                    slide.setToY(600);
                    slide.play();
                    genres.setEffect(null);
                    unBlur();
                    tf1.setVisible(true);
                    ((Listener) account).setFavouriteGenres(new ArrayList<>(genreList));
                    System.out.println(((Listener) account).getFavouriteGenres());
                    GetSuggestions getSuggestions = new GetSuggestions();
                    getSuggestions.setAccount((Listener) account);
                    suggestions = getSuggestions.giveSuggestions();
                        try {
                            for (Audio mostLikedSong : suggestions) {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                                VBox vBox = fxmlLoader.load();
                                SongController songController = fxmlLoader.getController();
                                songController.setInfo(mostLikedSong);
                                title.setText(mostLikedSong.getTitle());
                                musics = mostLikedSong;
                                songController.play.setOnMouseClicked(event2 ->
                                {
                                    mostLikedSong.setPlays(mostLikedSong.getPlays() + 1);
                                    title.setText(mostLikedSong.getTitle());
                                    imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                                    artistv.setText(mostLikedSong.getArtist());
                                    Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                                    lyrics.setText(((Music) mostLikedSong).getLyrics());
                                    coverFlow.setImage(pause5);
                                    if(mediaPlayer != null)
                                        mediaPlayer.stop();
                                    media = new Media(mostLikedSong.getLink());
                                    mediaPlayer = new MediaPlayer(media);
                                    try {
                                        play_pause(event);
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                topArtists.getChildren().add(vBox);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    System.out.println(suggestions.size());
                }
            }
        });
        signup.setText("Sign Up");
        double fontSize = 15;
        FontWeight fontWeight = FontWeight.NORMAL;
        Font font = Font.font(null, fontWeight, null, fontSize);
        Font font2 = Font.font("Arial", FontWeight.BOLD, null, fontSize);
        Color color = Color.web("#FFFFFF");
        Color color2 = Color.web("#000000");
        signup.setFill(color);
        sBox.setAlignment(Pos.CENTER);
        playlistSet();
        sBox.getChildren().add(signup);
        signup.setFont(font);
        user.getChildren().remove(menuButton);
        user.getChildren().remove(userPic);
        user.getChildren().add(login);
        login.setFill(color2);
        login.setText("Login");
        user.setAlignment(Pos.CENTER);
        menuButton.setStyle("-fx-background-color: black");
        login.setFont(font2);
        login.setFill(color2);
        user.setStyle("-fx-background-color: white");
        mainAnc.getChildren().remove(pInfo);
        listenerP.getChildren().add(pInfo);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("pop", ListenerController.getListenerController().filterAudioFiles('G',"POP").size()),
                new PieChart.Data("rock", ListenerController.getListenerController().filterAudioFiles('G',"ROCK").size()),
                new PieChart.Data("jazz", ListenerController.getListenerController().filterAudioFiles('G',"JAZZ").size()),
                new PieChart.Data("interview", ListenerController.getListenerController().filterAudioFiles('G',"INTERVIEW").size()));
        System.out.println(ListenerController.getListenerController().filterAudioFiles('G',"POP").size());
        pie.setData(pieChartData);
        pie.setTitle("top genres");
        vSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(vSlider.getValue() * 0.01);
            }
        });
        setArtistList();
        int c = 1;

        for(Report report : DataBase.getDataBase().getReports())
        {
            repos.getItems().add(report);
            repos.setOnMouseClicked(e -> {
                from.setText("from: " + report.getReporter().getUserName());
                to.setText("to: " + report.getReported().getUserName());
                des.setText(report.getDescription());
                repp1.setVisible(true);
            });
        }
    }
    Timer timer;
    TimerTask task;
    boolean running;
    @FXML private ProgressBar songPBar = new ProgressBar();
    @FXML private VBox artistList2;
    @FXML private VBox artistList21;
    public void setArtistList()
    {
        try {
            for (UserAccount userAccount : getDataBase().getUsers()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("artistAdmin.fxml"));
                HBox vBox = fxmlLoader.load();
                ArtistAdminController artistAdminController = fxmlLoader.getController();
                if(userAccount instanceof Artist) {
                    artistAdminController.set((Artist) userAccount);
                    artistList2.getChildren().add(vBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void beginTimer()
    {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songPBar.setProgress(current/end);
                if(current/end == 1)
                {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    public void cancelTimer()
    {
        running = false;
        timer.cancel();
    }

    public void playlistSet() {
        if (this.account != null) {
            playlists = new ArrayList<Playlist>();
            System.out.println(playlists);
            playlists.addAll(((Listener) account).getPlaylists());
            try {
                for (Playlist playlist1 : playlists) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Playlist.fxml"));
                    VBox vBox = fxmlLoader.load();
                    PlaylistController playlistController = fxmlLoader.getController();
                    playlistController.setPlaylists(playlist1);
                    playlistController.showPInfo.setOnMouseClicked(event1 ->
                    {
                        scPlaylist.setVisible(true);
                        firstTitle.setText(playlist1.getName());
                        allPanes.add(scPlaylist);
                        scPlaylist.setVisible(true);
                        List <Audio> audioList = new ArrayList<>(playlist1.getAudiosList());
                        for (Audio audio : audioList) {
                            try {
                                FXMLLoader fxmlLoader2 = new FXMLLoader();
                                fxmlLoader2.setLocation(getClass().getResource("playlistElement.fxml"));
                                HBox hBox = fxmlLoader2.load();;
                                PlaylistElementController playlistElementController = fxmlLoader2.getController();
                                playlistElementController.showPsong.setOnMouseClicked(event2 -> {
                                    scPlaylist.setVisible(true);
                                    title.setText(musics.getTitle());
                                    imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm()));
                                    artistv.setText(musics.getArtist());
                                    Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(musics.getCover())).toExternalForm());
                                    if(musics instanceof Music)
                                        lyrics.setText(((Music) musics).getLyrics());
                                    else
                                        lyrics.setText(((Podcast) musics).getCaption());
                                    coverFlow.setImage(pause5);
                                    if(mediaPlayer != null)
                                        mediaPlayer.stop();
                                    media = new Media(audio.getLink());
                                    mediaPlayer = new MediaPlayer(media);
                                    try {
                                        play_pause(event2);
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                playlistElementController.setAudios(audio);
                                playlistMusics.getChildren().add(hBox);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    playlist.getChildren().clear();
                    playlist.getChildren().add(vBox);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void mostPSet(char base) {
        List<Audio> audioList;
        if(base == 'L') {
             audioList = new ArrayList<>(ListenerController.getListenerController().sortAudioFiles('L'));
        }
        else
        {
             audioList = new ArrayList<>(ListenerController.getListenerController().sortAudioFiles('P'));

        }
        while(audioList.size() != 10)
        {
            audioList.remove(audioList.getLast());
        }
        XYChart.Series<String,Number> s1 = new XYChart.Series<>();
        for(Audio audio : DataBase.getDataBase().getAudioFiles())
        {
                for(Audio audio1 : audioList)
                {
                    if(base == 'L') {
                        if (audio.getTitle() == audio1.getTitle()) {
                            s1.getData().add(new XYChart.Data<>(audio.getTitle(), audio.getLikes()));
                        }
                    }if(base == 'P') {
                        if (audio.getTitle() == audio1.getTitle()) {
                            s1.getData().add(new XYChart.Data<>(audio.getTitle(), audio.getPlays()));
                        }
                    }
                }
        }
        analystics.setLegendVisible(false);
        analystics.getData().addAll(s1);
        Node fill = s1.getNode().lookup(".chart-series-area-fill");
        Node line = s1.getNode().lookup(".chart-series-area-line");
        Color color3 = Color.web("#1c0000");

        String rgb = String.format("%d, %d, %d",
                (int) (color3.getRed() * 255),
                (int) (color3.getGreen() * 255),
                (int) (color3.getBlue() * 255));

        fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
        line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
        for (Audio audio : audioList) {
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(getClass().getResource("AdminChart.fxml"));
                HBox hBox = fxmlLoader2.load();
                AdminChartController adminChartController = fxmlLoader2.getController();
                adminChartController.t = true;
                adminChartController.setAudios(audio);
                mostP_L.getChildren().add(hBox);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Audio> getLikedMus() {
        ArrayList<Audio> list = new ArrayList<>();
        Music music1 = new Music("Cry", "Ciggarets after", "POP", "https://ts12.tarafdari.com/contents/user742612/content-sound/sufjan_stevens_-_fourth_of_july.mp3", "IMG_9614.JPG", "fourth of july");
        Music music2 = new Music("Fourth of july", "Sufjan Stevens", "POP", "https://ts13.tarafdari.com/contents/user760078/content-sound/87292276.mp3", "7f53e3ec9752c0f901d9d1370b569507.1000x1000x1.jpg", "cry");
        list.add(music2);
        list.add(music1);
        return list;
    }
    public void login(MouseEvent event)
    {
        if (account == null) {

            s_ll.setMaxSize(600, 400);
            sc1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            BoxBlur blur = new BoxBlur();
            TranslateTransition slide = new TranslateTransition();
            s_ll.setVisible(true);
            slide.setDuration(Duration.seconds(0.7));
            s_ll.setMinSize(500, 300);
            slide.setNode(s_ll);
            slide.setToY(-850);
            slide.play();
            info.getChildren().remove(vpas);
            info.getChildren().remove(pas);
            info.getChildren().add(pas);
            info.getChildren().remove(passInE);
            info.getChildren().add(passInE);
            s_ll.setEffect(null);
            h1.setEffect(blur);
            h2.setEffect(blur);
            h3.setEffect(blur);
            h5.setEffect(blur);
            h6.setEffect(blur);
            sBox.setEffect(blur);
            user.setEffect(blur);
            mainV.setEffect(blur);
            top.setEffect(blur);
            logo2.setEffect(blur);
            tf1.setVisible(false);
        }
        if (account != null) {
            menuItem.setVisible(true);
            menuButton.setVisible(true);
        }

    }

    public boolean tryCatch() {
        boolean f = false;
        if (pas1 != null)
            pass.setText(pas1.getText());
        try {
            ExceptionHandler.getExceptionHandler().passwordCheck(log.getText(), pass.getText());
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                usernameInE.setVisible(false);
            });
            pass.textProperty().addListener((observable, oldValue, newValue) -> {

            });
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            usernameInE.setVisible(true);
            f = true;
            log.setText(null);
        } catch (WrongPaswordException e) {
            System.out.println(e.getMessage());
            passInE.setVisible(true);
            f = true;
            pass.setText(null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return f;
    }

    public void invisible(MouseEvent event) {
        pass.setText(pas1.getText());
        info.getChildren().remove(pas);
        info.getChildren().add(vpas);
        info.getChildren().remove(passInE);
        info.getChildren().add(passInE);
    }

    public List<Artist> getData() {
        List<Artist> artists = new ArrayList<>();
        for (UserAccount userAccount : getDataBase().getUsers()) {
            if (userAccount instanceof Artist) {
                artists.add((Artist) userAccount);
            }
        }
        return artists;
    }
    public ArrayList<Listener> getData2() {
        ArrayList<Listener> artists = new ArrayList<>();
        for (UserAccount userAccount : getDataBase().getUsers()) {
            if (userAccount instanceof Listener) {
                artists.add((Listener) userAccount);
            }
        }
        return artists;
    }

    public void visible(MouseEvent event) {
        pas1.setText(pass.getText());
        info.getChildren().remove(vpas);
        info.getChildren().add(pas);
        info.getChildren().remove(passInE);
        info.getChildren().add(passInE);
    }

    public void sbox1(MouseEvent event) throws IOException {
        s_ll.setVisible(false);
        signup(event);
    }
    private boolean singer = false;
    public void singer(MouseEvent event) {
        singer = true;
        Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-25.jpeg")).toExternalForm());
        box.setFill(new ImagePattern(image));
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(2));
        slide1.setNode(type);
        slide1.setToY(500);
        slide1.play();
        sc1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        BoxBlur blur = new BoxBlur();
        TranslateTransition slide = new TranslateTransition();
        sc1.setVisible(true);
        slide.setDuration(Duration.seconds(1.5));
        slide.setNode(sc1);
        slide.setToY(-655);
        slide.play();
        sc1.setEffect(null);
        h1.setEffect(blur);
        h2.setEffect(blur);
        h3.setEffect(blur);
        h5.setEffect(blur);
        h6.setEffect(blur);
        sBox.setEffect(blur);
        user.setEffect(blur);
        mainV.setEffect(blur);
        top.setEffect(blur);
        logo2.setEffect(blur);
        tf1.setVisible(false);
        bio.setVisible(true);
        bio2.setVisible(true);
    }
    private boolean listener = false;
    public void listener(MouseEvent event) {
        listener = true;
        Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-111.jpeg")).toExternalForm());
        box.setFill(new ImagePattern(image));
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(2));
        slide1.setNode(type);
        slide1.setToY(500);
        slide1.play();
        sc1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        BoxBlur blur = new BoxBlur();
        TranslateTransition slide = new TranslateTransition();
        sc1.setVisible(true);
        slide.setDuration(Duration.seconds(1.5));
        slide.setNode(sc1);
        slide.setToY(-655);
        slide.play();
        sc1.setEffect(null);
        h1.setEffect(blur);
        h2.setEffect(blur);
        h3.setEffect(blur);
        h5.setEffect(blur);
        h6.setEffect(blur);
        sBox.setEffect(blur);
        user.setEffect(blur);
        mainV.setEffect(blur);
        tf1.setVisible(false);
        bio.setVisible(false);
        bio2.setVisible(false);
    }

    public void podcaster(MouseEvent event) {
        podcasterAcc = true;
        Image image = new Image(Objects.requireNonNull(BaseHomeController.class.getResource("images-22.jpeg")).toExternalForm());
        box.setFill(new ImagePattern(image));
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(2));
        slide1.setNode(type);
        slide1.setToY(500);
        slide1.play();
        sc1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        BoxBlur blur = new BoxBlur();
        TranslateTransition slide = new TranslateTransition();
        sc1.setVisible(true);
        slide.setDuration(Duration.seconds(1.5));
        slide.setNode(sc1);
        slide.setToY(-655);
        slide.play();
        sc1.setEffect(null);
        h1.setEffect(blur);
        h2.setEffect(blur);
        h3.setEffect(blur);
        h5.setEffect(blur);
        h6.setEffect(blur);
        sBox.setEffect(blur);
        user.setEffect(blur);
        mainV.setEffect(blur);
        tf1.setVisible(false);
        bio.setVisible(true);
        bio2.setVisible(true);
    }
    public void signup(MouseEvent event) throws IOException
    {
        if (account == null) {
            TranslateTransition slide = new TranslateTransition();
            type.setVisible(true);
            slide.setDuration(Duration.seconds(1));
            slide.setNode(type);
            slide.setToY(-500);
            slide.play();
            type.setEffect(null);
            blur();
            tf1.setVisible(false);
        }
        else {
            allPanes.add(premium);
            pages.add(premium);
            premium.setVisible(true);
        }
    }

    public void blur() {
        BoxBlur blur = new BoxBlur();
        h1.setEffect(blur);
        h2.setEffect(blur);
        h3.setEffect(blur);
        h5.setEffect(blur);
        h6.setEffect(blur);
        sBox.setEffect(blur);
        user.setEffect(blur);
        mainV.setEffect(blur);
        top.setEffect(blur);
        logo2.setEffect(blur);
    }

    public void unBlur() {
        sc1.setEffect(null);
        h1.setEffect(null);
        h2.setEffect(null);
        h3.setEffect(null);
        h5.setEffect(null);
        h6.setEffect(null);
        sBox.setEffect(null);
        user.setEffect(null);
        mainV.setEffect(null);
        logo2.setEffect(null);
        top.setEffect(null);
    }

    public void changeTopBar(UserAccount userAccount) {
        tf1.setVisible(true);
        signup.setText("UPGRADE");
        FontWeight fontWeight = FontWeight.NORMAL;
        Font font = Font.font(null, fontWeight, null, 12);
        signup.setFont(font);
        user.getChildren().add(userPic);
        login.setText(userAccount.getUserName());
        login.setFill(Color.WHITE);
        user.getChildren().add(menuButton);
        user.setStyle("-fx-background-color: black");
        user.setDisable(false);
        sBox.setDisable(false);
        TranslateTransition slide2 = new TranslateTransition();
        p.setVisible(true);
        slide2.setDuration(Duration.seconds(2));
        if(account instanceof Listener) {
            slide2.setNode(p);
            slide2.setToY(-755);
            slide2.play();
        }
        imgv.setImage(null);
        artistv.setText(null);
        title.setText(null);
    }

    public void complete(MouseEvent event) throws UserNotFoundException, ParseException, InvalidFormatException, NotAvailableId {
        boolean b = false;
        tryCatch(fullname,fullnameE);
        tryCatch(password,passwordE);
        tryCatch(birthdate,birthdateE);
        b=tryCatch(b);
        System.out.println(b);
        if(listener && !b)
        {
            dashboard.setText("Your Library");
            pou.setText("playlists");
            sc6.setVisible(true);
            sugg.setVisible(true);
            addp.setVisible(true);
            sBox.setVisible(true);
            topArtists.setVisible(true);
            if(birthdate.getValue() != null) {
                SignupController.getSignupController().signup('L', username.getText(), password.getText(), fullname.getText(), email.getText(), number.getText(), birthdate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), null);
            }
            else {
                try {
                    birthdateE.setVisible(true);
                }
                catch (NullPointerException ex) {
                    throw new NullPointerException();
                }
            }
        }
        else if(podcasterAcc && !b)
        {
            dashboard.setText("Your Library");
            pou.setText("overview");
            p.setVisible(false);
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                count = 1;
            }
            sc6.setVisible(false);
            sugg.setVisible(false);
            addp.setVisible(false);
            sBox.setVisible(false);
            topArtists.setVisible(false);
            if(birthdate.getValue() != null) {
                SignupController.getSignupController().signup('P', username.getText(), password.getText(), fullname.getText(), email.getText(), number.getText(), birthdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), bio2.getText());
            }
            else {
                try {
                    birthdateE.setVisible(true);
                }
                catch (NullPointerException ex) {
                    throw new NullPointerException();
                }
            }
        }
        else if(singer && !b){
            dashboard.setText("Your Library");
            pou.setText("overview");
            p.setVisible(false);
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                count = 1;
            }
            sc6.setVisible(false);
            sugg.setVisible(false);
            addp.setVisible(false);
            sBox.setVisible(false);
            topArtists.setVisible(false);

            if(birthdate.getValue() != null) {
                SignupController.getSignupController().signup('S', username.getText(), password.getText(), fullname.getText(), email.getText(), number.getText(), birthdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), bio2.getText());
            }
            else {
                try {
                    birthdateE.setVisible(true);
                }
                catch (NullPointerException ex) {
                    throw new NullPointerException();
                }
            }
        }
        account = SignupController.getSignupController().getAccount();
        GetSuggestions getSuggestions = new GetSuggestions();
        if(account instanceof Listener)
            getSuggestions.setAccount((Listener) account);
//        suggestions = getSuggestions.giveSuggestions(3);
        sc6.setVisible(true);

        if(account instanceof Listener) {
            ls = new ArrayList<>(((Listener) account).getLikedAudioFiles());
        }
        if(!b) {
            library.setVisible(true);
            if((account instanceof Listener))
            {
                try {
                    for (Audio mostLikedSong : suggestions) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                        VBox vBox = fxmlLoader.load();
                        SongController songController = fxmlLoader.getController();
                        songController.setInfo(mostLikedSong);
                        title.setText(mostLikedSong.getTitle());
                        musics = mostLikedSong;
                        songController.play.setOnMouseClicked(event2 ->
                        {
                            mostLikedSong.setPlays(mostLikedSong.getPlays() + 1);
                            title.setText(mostLikedSong.getTitle());
                            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                            artistv.setText(mostLikedSong.getArtist());
                            Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                            lyrics.setText(((Music) mostLikedSong).getLyrics());
                            coverFlow.setImage(pause5);
                            if(mediaPlayer != null)
                                mediaPlayer.stop();
                            media = new Media(mostLikedSong.getLink());
                            mediaPlayer = new MediaPlayer(media);
                            try {
                                play_pause(event2);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        topArtists.getChildren().add(vBox);

                    }
                } catch(IOException e){
                    throw new RuntimeException(e);
                }
            }
            numberE.setVisible(false);
            TranslateTransition slide1 = new TranslateTransition();
            slide1.setDuration(Duration.seconds(1.5));
            slide1.setNode(sc1);
            slide1.setToY(655);
            slide1.play();
            unBlur();
            fullname.setText(null);
            email.setText(null);
            birthdate.setValue(null);
            password.setText(null);
            username.setText(null);
            number.setText(null);
            bio2.setText(null);
            changeTopBar(account);
            tf1.setVisible(false);
            if(account instanceof Listener) {
                TranslateTransition slide = new TranslateTransition();
                genres.setVisible(true);
                slide.setDuration(Duration.seconds(1));
                slide.setNode(genres);
                slide.setToY(-600);
                slide.play();
                genres.setEffect(null);
                blur();
                tf1.setVisible(false);
            }
        }
    }


    public boolean tryCatch(boolean b) {
        try {
            ExceptionHandler.getExceptionHandler().NumberValidation(number.getText());
            number.textProperty().addListener((observable, oldValue, newValue) -> {
                numberE.setVisible(false);
            });
        } catch (InvalidFormatException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            b = true;
            numberE.setVisible(true);
            number.setText(null);
        }
        try {
            ExceptionHandler.getExceptionHandler().checkUsername(username.getText());
            username.textProperty().addListener((observable, oldValue, newValue) -> {
            });
        } catch (UserNotFoundException | NullPointerException | NotAvailableId ex) {
            System.out.println(ex.getMessage());
            b = true;
            usernameE.setVisible(true);
        }
        try {
            ExceptionHandler.getExceptionHandler().EmailValidation(email.getText());
            email.textProperty().addListener((observable, oldValue, newValue) -> {
                emailE.setVisible(false);
            });
        } catch (InvalidFormatException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            b = true;
            emailE.setVisible(true);
            email.setText(null);
        }
        return b;
    }

    public void tryCatch(TextField text, Label label) {
        try {
            ExceptionHandler.getExceptionHandler().emptyCheck(text.getText());
            text.textProperty().addListener((observable, oldValue, newValue) -> {
            });
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            label.setVisible(true);
        }
    }

    public void tryCatch(DatePicker date, Label label) {
        try {
            date.valueProperty().addListener((observable, oldValue, newValue) -> {
            });
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            label.setVisible(true);
        }
    }
    private boolean mode = false;
    @FXML private HBox topBar;
    public void mode(MouseEvent event) {
        mode = !mode;
        if(mode == true) {
            slider.getStylesheets().add("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/light.css");
            slider.getStylesheets().remove("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/dark.css");
            logo2.getStylesheets().remove("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/dark.css");
            logo2.getStylesheets().add("\"file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/light.css\"");
            topBar.getStylesheets().remove("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/dark.css");
            topBar.getStylesheets().add("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/light.css");
            slider.getStylesheets().remove("\"file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/dark.css\"");
            mainScroll.getStylesheets().add("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/light.css");
            mainScroll.getStylesheets().add("file:/Users/parinaz/IdeaProjects/firstproject-musicplayer-phase2-ParinazMehrabi11/Graphic/target/classes/org/example/graphic/dark.css");
        }
    }

    public void loginfix(MouseEvent event) {
        boolean b = tryCatch();
        if (!b) {
            TranslateTransition slide1 = new TranslateTransition();
            slide1.setDuration(Duration.seconds(2));
            slide1.setNode(s_ll);
            slide1.setToY(850);
            slide1.play();
            sc6.setVisible(true);
            sc1.setEffect(null);
            h1.setEffect(null);
            h2.setEffect(null);
            usernameInE.setVisible(false);
            passInE.setVisible(false);
            h3.setEffect(null);
            h5.setEffect(null);
            h6.setEffect(null);
            sBox.setEffect(null);
            user.setEffect(null);
            mainV.setEffect(null);
            logo2.setEffect(null);
            top.setEffect(null);
            tf1.setVisible(true);
            library.setVisible(true);
            signup.setText("UPGRADE");
            FontWeight fontWeight = FontWeight.BOLD;
            Font font = Font.font(null, fontWeight, null, 12);
            signup.setFont(font);
            user.getChildren().remove(login);
            user.getChildren().add(login);
            for(UserAccount userAccount : getDataBase().getUsers())
            {
                if(Objects.equals(userAccount.getUserName(), log.getText()))
                    this.account = userAccount;
            }
            if(account instanceof Listener)
            {
                sc6.setVisible(true);
                sugg.setVisible(true);
                addp.setVisible(true);
                inf.setVisible(true);
                sBox.setVisible(true);
                library.setVisible(true);
                topArtists.setVisible(true);
                dashboard.setText("Your library");
                pou.setText("Playlists");
                inf.setVisible(true);
                scAdmin.setVisible(false);
            }
            else if(account instanceof Admin)
            {
                sugg.setVisible(false);
                addp.setVisible(false);
                sBox.setVisible(false);
                library.setVisible(true);
                topArtists.setVisible(true);
                dashboard.setText("Dashboard");
                pou.setText("Listeners");
                inf.setVisible(false);
                sc6.setVisible(false);
            }
            else {
                sc6.setVisible(false);
                sugg.setVisible(false);
                addp.setVisible(false);
                inf.setVisible(false);
                sBox.setVisible(false);
                scAdmin.setVisible(false);
                library.setVisible(true);
                topArtists.setVisible(false);
                pou.setText("overview");
            }
            login.setText(account.getUserName());
            login.setFill(Color.WHITE);
            user.getChildren().add(menuButton);
            user.setStyle("-fx-background-color: black");
            TranslateTransition slide2 = new TranslateTransition();
            if(account instanceof Listener) {
                slide2.setDuration(Duration.seconds(2));
                slide2.setNode(p);
                slide2.setToY(-755);
                slide2.play();
            }
            pass.setText(null);
            pas1.setText(null);
            log.setText(null);
            imgv.setImage(null);
            artistv.setText(null);
            title.setText(null);
            try {
                for (Audio mostLikedSong : suggestions) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                    VBox vBox = fxmlLoader.load();
                    SongController songController = fxmlLoader.getController();
                    songController.setInfo(mostLikedSong);
                    title.setText(mostLikedSong.getTitle());
                    musics = mostLikedSong;
                    songController.play.setOnMouseClicked(event2 ->
                    {
                        mostLikedSong.setPlays(mostLikedSong.getPlays() + 1);
                        title.setText(mostLikedSong.getTitle());
                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm()));
                        artistv.setText(mostLikedSong.getArtist());
                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(mostLikedSong.getCover())).toExternalForm());
                        lyrics.setText(((Music) mostLikedSong).getLyrics());
                        coverFlow.setImage(pause5);
                        if(mediaPlayer != null)
                            mediaPlayer.stop();
                        media = new Media(mostLikedSong.getLink());
                        mediaPlayer = new MediaPlayer(media);
                        try {
                            play_pause(event2);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    topArtists.getChildren().add(vBox);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private Label userName;
    @FXML
    private Label userInfo;
    @FXML
    private Label fullname2;
    @FXML
    private Label fullname21;
    @FXML
    private Label username2;
    @FXML
    private Label username21;
    @FXML
    private Label password2;
    @FXML
    private Label password21;
    @FXML private Label email21;
    @FXML private Label number21;
    @FXML private Label credit21;
    @FXML private ScrollPane sc6;
    @FXML private ScrollPane scAdmin;
    @FXML
    private Label email2;
    @FXML
    private Label birthdate2;
    @FXML
    private Label number2;
    @FXML
    private Label credit2;
    @FXML
    private Label firstTitle;
    @FXML
    private Label deadline;

    public void player(MouseEvent event) {
        if(account instanceof Listener && account != null) {
            allPanes.add(sc);
            pages.add(sc);
            scA.setVisible(false);
            audiosSc.setVisible(false);
            scPlaylist.setVisible(false);
            sc.setVisible(true);
            if (account != null) {
                userName.setText(account.getUserName());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((Listener) account).getPlaylists().size());
                stringBuilder.append(" Public playlists . ");
                stringBuilder.append(((Listener) account).getFollowings().size());
                stringBuilder.append(" followings");
                userInfo.setText(stringBuilder.toString());
            }
            fullname2.setText(account.getFullName());
            username2.setText(account.getUserName());
            password2.setText(account.getPassword());

            email2.setText(account.getEmail());
            birthdate2.setText(String.valueOf(account.getBirthDate()));
            number2.setText(account.getPhoneNumber());
//       deadline.setText(formatter2.format(((Listener) account).getPremiumDeadline()));
            credit2.setText(String.valueOf(((Listener) account).getAccountCredit()));
            System.out.println(((Listener) account).getFollowings().size());
            following.getChildren().clear();
            followings = new ArrayList<Artist>((((Listener) account).getFollowings()));
            if (((Listener) account).getFollowings() != null) {
                try {
                    for (Artist artist : followings) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("followings.fxml"));
                        VBox vBox = fxmlLoader.load();
                        FollowingController followingController = fxmlLoader.getController();
                        followingController.show.setOnMouseClicked(event1 ->
                        {
                            followers.setText(String.valueOf(((Artist) artist).getFollowers().size()));
                            biography.setText(((Artist) artist).getBiography());
                            if (account instanceof Singer) {
                                typeProf.setText("Singer");
                            } else {
                                typeProf.setText("Posdcaster");
                            }
                            usernameArtist.setText(((Artist) artist).getUserName());
                            ArrayList<Audio> musics = new ArrayList<>(((Artist) artist).getReleases());
                            allPanes.add(sc2);
                            pages.add(sc2);
                            sc2.setVisible(true);
                            try {
                                for (Audio music : musics) {
                                    FXMLLoader fxmlLoader3 = new FXMLLoader();
                                    fxmlLoader3.setLocation(getClass().getResource("song.fxml"));
                                    VBox vBox2 = fxmlLoader3.load();
                                    SongController songController = fxmlLoader3.getController();
                                    songController.setInfo(music);
                                    title.setText(music.getTitle());
                                    songController.play.setOnMouseClicked(event3 ->
                                    {
                                        music.setPlays(music.getPlays() + 1);
                                        title.setText(music.getTitle());
                                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm()));
                                        artistv.setText(music.getArtist());
                                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm());
                                        lyrics.setText(((Music) music).getLyrics());
                                        this.musics = music;
                                        coverFlow.setImage(pause5);
                                        if(mediaPlayer != null)
                                            mediaPlayer.stop();
                                        media = new Media(music.getLink());
                                        mediaPlayer = new MediaPlayer(media);
                                        try {
                                            play_pause(event3);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    lastRelease.getChildren().add(vBox2);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        followingController.setFollowing(artist);
                        following.getChildren().add(vBox);

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        else if(account instanceof Admin && account != null)
        {
            int c = 1;
            allPanes.add(scAdmin);
            pages.add(scAdmin);
            scAdmin.setVisible(true);
            scA1.setVisible(false);
            audiosSc.setVisible(false);
            scA.setVisible(false);
            allPanes.add(scAdmin);
            int l = 0;
            for(UserAccount userAccount : DataBase.getDataBase().getUsers())
        {
            if(userAccount instanceof Listener)
                l++;
        }
            LC.setText(String.valueOf(l));
            int p = 0;
            for(Audio audio: DataBase.getDataBase().getAudioFiles())
            {
                    p += (int) audio.getPlays();
            }
            PC.setText(String.valueOf(p));
            int a = 0;
            for(UserAccount userAccount : DataBase.getDataBase().getUsers())
            {
                if(userAccount instanceof Artist)
                    a++;
            }
            AC.setText(String.valueOf(a));
        }
        else if((account instanceof Artist  || account instanceof Podcaster) && account != null)
        {
            scAdmin.setVisible(false);
            lastRelease1.getChildren().clear();
            usernameArtist1.setText(account.getUserName());
            int c = 1;
            sc21.setVisible(true);
            allPanes.add(sc21);
            ArrayList<Audio> audio = new ArrayList<>(((Artist)account).getReleases());
            try {
                for (Audio mostLikedSong : audio) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                    VBox vBox = fxmlLoader.load();
                    SongController songController = fxmlLoader.getController();
                    songController.setInfo(mostLikedSong);
                    title.setText(mostLikedSong.getTitle());
                    lastRelease1.getChildren().add(vBox);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @FXML private Label usernameArtist1;
    @FXML private Label sugg;
    public void personal(MouseEvent event) {
        pInfo.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(pInfo);
        slide.setToY(-650);
        slide.play();
    }

    public void ok(MouseEvent event) {
        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(2));
        slide1.setNode(pInfo);
        slide1.setToY(650);
        slide1.play();
    }

    @FXML
    private TextField playlistTitle;
    @FXML
    private Label playlistLimit;
    @FXML
    private Button errorBtn;

    public void create(MouseEvent event) {
        boolean limit = false;
        try {
            ListenerController.getListenerController().setAccount((Listener) account);
            ListenerController.getListenerController().createPlaylist(playlistTitle.getText());
        } catch (FreeAccountLimitException e) {
            System.out.println(e.getMessage());
            playlistLimit.setVisible(true);
            limit = true;
        }
        if (limit) {
            errorBtn.setVisible(true);
        } else {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(2));
            slide.setNode(playlistName);
            slide.setToY(650);
            slide.play();
            playlistSet();
        }
        playlistTitle.setText(null);
    }
    @FXML
    ImageView like;
    @FXML
    ImageView liked;

    public void like(MouseEvent event)
    {
        try {
            musics.setLikes(musics.getLikes() + 1);
            like.setVisible(false);
            liked.setVisible(true);
            ListenerController.getListenerController().setAccount((Listener) account);
            ListenerController.getListenerController().likeAudioFile(musics.getId());
            ((Listener) account).getLikedAudioFiles().add(musics);
            musics.setLikes(musics.getLikes() + 1);
            System.out.println(((Listener) account).getLikedAudioFiles().getLast());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());;
        } catch (LikedBefore e) {
            throw new RuntimeException(e);
        }

    }

    public void follow(MouseEvent event) {
        for (UserAccount userAccount : getDataBase().getUsers()) {
            if (userAccount instanceof Artist && userAccount.getUserName() == usernameArtist.getText()) {
                boolean t = false;
                for (UserAccount userAccount1 : ((Artist) userAccount).getFollowers()) {
                    if (userAccount1 == account) {
                        t = true;
                    }
                }
                if (!t) {
                    ((Artist) userAccount).getFollowers().add(account);
                    followers.setText(String.valueOf(((Artist) userAccount).getFollowers().size()));
                    ((Listener) account).getFollowings().add((Artist) userAccount);
                }
            }
        }
    }

    public void report(MouseEvent event) {
        ListenerController.getListenerController().recordReport(usernameArtist.getText(), reportBox.getText());
        reportBox.setText(null);
    }

    public void over(MouseEvent event) {
        overview.setStyle("-fx-border-width: 0 0 4 0 ");
    }

    public void view(MouseEvent event) {
        overview.setStyle("-fx-border-width: 0 0 0 0 ");
    }

    public void aboutE(MouseEvent event) {
        about.setStyle("-fx-border-width: 0 0 4 0 ");
    }

    public void aboutEx(MouseEvent event) {
        about.setStyle("-fx-border-width: 0 0 0 0 ");
    }

    public void rock(MouseEvent event) {
        r1t = !r1t;
        if (r1t) {
            r1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            r1.setStyle("-fx-background-color: #181818");
        }

    }

    public void popS(MouseEvent event) {
        p1t = !p1t;
        if (p1t) {
            p1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            p1.setStyle("-fx-background-color: #181818");
        }
    }

    public void p() {
        menuItem.setOnAction(actionEvent -> {
            LogoutController logoutController = new LogoutController(account);
            topLabel.setText("Top Musics");
            library.setVisible(false);
            account = null;
            logoutController.logout();
            sc6.setVisible(false);
            signup.setText("Sign Up");
            scAdmin.setVisible(false);
            sBox.setVisible(true);
            double fontSize = 15;
            FontWeight fontWeight = FontWeight.NORMAL;
            Font font = Font.font(null, fontWeight, null, 12);
            Font font2 = Font.font("Arial", FontWeight.BOLD, null, fontSize);
            Color color = Color.web("#FFFFFF");
            Color color2 = Color.web("#000000");
            signup.setFill(color);
            sBox.setAlignment(Pos.CENTER);
            sBox.getChildren().remove(signup);
            sBox.getChildren().add(signup);
            signup.setFont(font);
            user.getChildren().remove(menuButton);
            user.getChildren().remove(userPic);
            login.setFill(color2);
            login.setText("Login");
            user.setAlignment(Pos.CENTER);
            login.setFont(font2);
            login.setFill(color2);
            user.setStyle("-fx-background-color: white");
            sc1.setVisible(false);
            type.setVisible(false);
            user.setDisable(false);
            sBox.setDisable(false);
            anc.setVisible(true);
            mainScroll.setVisible(true);
            for(Node node : allPanes)
            {
                node.setVisible(false);
            }
            anc.setVisible(true);
            mainScroll.setVisible(true);
            TranslateTransition slide2 = new TranslateTransition();
            p.setVisible(true);
            slide2.setDuration(Duration.seconds(2));
            slide2.setNode(p);
            slide2.setToY(755);
            slide2.play();
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                count = 1;
            }
//            anc.getChildren().remove(mainScroll);
//            anc.getChildren().add(mainScroll);
        });
    }

    public void jazzS(MouseEvent event) {
        j1t = !j1t;
        if (j1t) {
            j1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
//            ((Listener)account).getFavouriteGenres().addAll()
        } else {
            j1.setStyle("-fx-background-color: #181818");
        }
    }

    public void trueS(MouseEvent event) {
        t1t = !t1t;
        if (t1t) {
            t1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            t1.setStyle("-fx-background-color: #181818");
        }
    }

    public void countS(MouseEvent event) {
        c1t = !c1t;
        if (c1t) {
            c1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            c1.setStyle("-fx-background-color: #181818");
        }
    }

    public void hiphopS(MouseEvent event) {
        hipt = !hipt;
        if (hipt) {
            hip.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            hip.setStyle("-fx-background-color: #181818");
        }
    }

    public void soS(MouseEvent event) {
        soci1t = !soci1t;

        if (soci1t) {
            soci1.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            soci1.setStyle("-fx-background-color: #181818");
        }
    }

    public void intS(MouseEvent event) {
        intert = !intert;
        if (intert) {
            inter.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            inter.setStyle("-fx-background-color: #181818");
        }
    }

    public void histS(MouseEvent event) {
        histort = !histort;
        if (histort) {
            histor.setStyle("-fx-background-color: #565050 ; -fx-background-radius: 20");
        } else {
            histor.setStyle("-fx-background-color: #181818");
        }
    }

    @FXML
    Label genreE;
    @FXML
    Button choice;
    @FXML
    private HBox scA;
    @FXML private Label addp;

    public void showGridArtists(MouseEvent event) {
        audiosSc.setVisible(false);
        scAdmin.setVisible(false);
        premium.setVisible(false);
        scA1.setVisible(false);
        sc21.setVisible(false);
        artists = getData();
        int column = 0;
       int  row = 0;
        try {
            for (Artist artist1 : artists) {
                lastRelease.getChildren().clear();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("artistAcc.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ArtistAccController artistAccController = fxmlLoader.getController();
                artistAccController.showInfo.setOnMouseClicked(event2 ->
                {
                    followers.setText(String.valueOf(artist1.getFollowers().size()));
                    biography.setText(artist1.getBiography());
                    if (artist1 instanceof Singer) {
                        typeProf.setText("Singer");
                    } else {
                        typeProf.setText("Posdcaster");
                    }
                    usernameArtist.setText(artist1.getUserName());
                    ArrayList<Audio> musics = new ArrayList<>(artist1.getReleases());
                    allPanes.add(sc2);
                    pages.add(sc2);
                    sc2.setVisible(true);
                    try {
                        for (Audio music : musics) {
                            FXMLLoader fxmlLoader2 = new FXMLLoader();
                            fxmlLoader2.setLocation(getClass().getResource("song.fxml"));
                            VBox vBox = fxmlLoader2.load();
                            SongController songController = fxmlLoader2.getController();
                            songController.setInfo(music);
                            title.setText(music.getTitle());
                            songController.play.setOnMouseClicked(event3 ->
                            {
                                music.setPlays(music.getPlays() + 1);
                                title.setText(music.getTitle());
                                imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm()));
                                artistv.setText(music.getArtist());
                                Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm());
                                lyrics.setText(((Music) music).getLyrics());
                                coverFlow.setImage(pause5);
                                if(mediaPlayer != null)
                                    mediaPlayer.stop();
                                media = new Media(music.getLink());
                                mediaPlayer = new MediaPlayer(media);
                                try {
                                    play_pause(event3);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            lastRelease.getChildren().add(vBox);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                artistAccController.setData(artist1);
                if (column == 8) {
                    column = 0;
                    row++;
                }
                GridPane.setConstraints(anchorPane, column++, row);
                gridArtists.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(0, 20, 20, 20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allPanes.add(scA);
        pages.add(scA);
        scA.setVisible(true);
    }
    public void showGridUsers(MouseEvent event) {
        ArrayList<Listener> listeners = getData2();
        scA1.setVisible(true);
        listeners = getData2();
        int column = 0;
        int  row = 0;
        try {
            for (Listener artist1 : listeners) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("userAcc.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                UserAccController artistAccController = fxmlLoader.getController();
                artistAccController.showInfo.setOnMouseClicked(event2 ->
                {
                    scA1.setVisible(false);
                    sc21.setVisible(false);
                    allPanes.add(sc);
                    pages.add(sc);
                    sc.setVisible(true);
                    userName.setText(artist1.getUserName());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((artist1).getPlaylists().size());
                    stringBuilder.append(" Public playlists . ");
                    stringBuilder.append((artist1).getFollowings().size());
                    stringBuilder.append(" followings");
                    userInfo.setText(stringBuilder.toString());
                    fullname2.setText(artist1.getFullName());
                    username2.setText(artist1.getUserName());
                    password2.setText(artist1.getPassword());
                    email2.setText(artist1.getEmail());
                    birthdate2.setText(String.valueOf(artist1.getBirthDate()));
                    number2.setText(artist1.getPhoneNumber());
//       deadline.setText(formatter2.format(((Listener) account).getPremiumDeadline()));
                    credit2.setText(String.valueOf(((Listener) artist1).getAccountCredit()));
                    System.out.println(((Listener) artist1).getFollowings().size());
                    following.getChildren().clear();
                    followings = new ArrayList<Artist>(((artist1).getFollowings()));
                    try {
                        for (Artist artist : followings) {
                            FXMLLoader fxmlLoader2 = new FXMLLoader();
                            fxmlLoader2.setLocation(getClass().getResource("followings.fxml"));
                            VBox vBox = fxmlLoader2.load();
                            FollowingController followingController = fxmlLoader2.getController();
                            followingController.setFollowing(artist);
                            following.getChildren().add(vBox);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                artistAccController.setData(artist1);
                if (column == 8) {
                    column = 0;
                    row++;
                }
                GridPane.setConstraints(anchorPane, column++, row);
                gridUsers.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(0, 20, 20, 20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        allPanes.add(scA1);
        pages.add(scA1);
        scA1.setVisible(true);
    }

    @FXML
    private ScrollPane scPlaylist;
    @FXML private Button addPl;
    @FXML private ListView<Report> repos;
    @FXML private Button pou;

    public void showplaylists(MouseEvent event) {
        audiosSc.setVisible(false);
        scA.setVisible(false);
        scA1.setVisible(false);
        if(account instanceof Listener)
        {
            premium.setVisible(false);
            allPanes.add(scPlaylist);
            pages.add(scPlaylist);
            scPlaylist.setVisible(true);
        }
        else if(account instanceof Admin)
        {
            addPl.setVisible(false);
            showGridUsers(event);
        } else if (account instanceof Artist) {
            player(event);
        }
    }

    public void audios(MouseEvent event) {
        allPanes.add(audiosSc);
        pages.add(audiosSc);
        audiosSc.setVisible(true);
        scAdmin.setVisible(false);
        scA1.setVisible(false);
        premium.setVisible(false);
        sc21.setVisible(false);
    }

    public void addPlaylist(MouseEvent event) {
        playlistName.setVisible(true);
    }

    public void add(MouseEvent event) {
        mainAnc.getChildren().remove(playlistName);
        mainAnc.getChildren().add(playlistName);
        playlistName.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(playlistName);
        slide.setToY(-650);
        slide.play();
    }

    public void close(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(playlistName);
        slide.setToY(650);
        slide.play();
    }

    @FXML
    private Label successful;
    @FXML
    private Label failed;
    @FXML
    private Label successmsg;
    @FXML
    private Label errormsg;
    @FXML
    private AnchorPane premiumResult;
    @FXML
    private Button errorBtn1;

    public void monthlyPurch(MouseEvent event) {
        //updateLeftDays;
        boolean enough = false;
        ListenerController.getListenerController().setAccount((Listener) account);
        try {
            ListenerController.getListenerController().purchasePremium(30);
            ListenerController.getListenerController().updateLeftDays();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughCredit e) {
            System.out.println(e.getMessage());
            enough = true;
            premiumResult.setVisible(true);
            errorBtn1.setVisible(true);
            errormsg.setVisible(true);
            failed.setVisible(true);
            closeP.setDisable(true);

            successmsg.setVisible(false);
            successful.setVisible(false);
        }
        if (!enough) {
            closeP.setDisable(false);
            successmsg.setVisible(true);
            successful.setVisible(true);
            failed.setVisible(false);
            ((Listener) account).setAccountCredit(((Listener) account).getAccountCredit() - 5);
            errormsg.setVisible(false);
            errorBtn.setVisible(false);
        }
        premiumResult.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(premiumResult);
        slide.setToY(-500);
        slide.play();
    }
    @FXML
    private Button errorBtn2;
    @FXML
    private Button addBtn;
    @FXML
    private TextField playlistName2;
    @FXML
    private Label playlistLimit1;
    @FXML private Label remainingTimeLabel;
    @FXML private Label currentTimeLabel;

    public void biPurch(MouseEvent event) {
        boolean enough = false;
        ListenerController.getListenerController().setAccount((Listener) account);
        try {
            ListenerController.getListenerController().purchasePremium(60);
            ListenerController.getListenerController().updateLeftDays();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughCredit e) {
            System.out.println(e.getMessage());
            enough = true;
            premiumResult.setVisible(true);
            errorBtn1.setVisible(true);
            errormsg.setVisible(true);
            failed.setVisible(true);
            closeP.setDisable(true);
            successmsg.setVisible(false);
            successful.setVisible(false);
        }
        if (!enough) {
            closeP.setDisable(false);
            successmsg.setVisible(true);
            successful.setVisible(true);
            failed.setVisible(false);
            errormsg.setVisible(false);
            ((Listener) account).setAccountCredit(((Listener) account).getAccountCredit() - 9);
            errorBtn.setVisible(false);
        }
        premiumResult.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(premiumResult);
        slide.setToY(-500);
        slide.play();
    }

    @FXML
    private Button closeP;
    @FXML
    private HBox bar;

    public void semiPerch(MouseEvent event) {

        boolean enough = false;
        ListenerController.getListenerController().setAccount((Listener) account);
        try {
            ListenerController.getListenerController().purchasePremium(180);
            ListenerController.getListenerController().updateLeftDays();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughCredit e) {
            System.out.println(e.getMessage());
            enough = true;
            premiumResult.setVisible(true);
            errorBtn1.setVisible(true);
            errormsg.setVisible(true);
            failed.setVisible(true);
            closeP.setDisable(true);
            successmsg.setVisible(false);
            successful.setVisible(false);
        }
        if (!enough) {
            closeP.setDisable(false);
            successmsg.setVisible(true);
            successful.setVisible(true);
            failed.setVisible(false);
            errormsg.setVisible(false);
            ((Listener) account).setAccountCredit(((Listener) account).getAccountCredit() - 14);
            errorBtn.setVisible(false);
        }
        premiumResult.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(premiumResult);
        slide.setToY(-500);
        slide.play();
    }

    public void close2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(premiumResult);
        slide.setToY(500);
        slide.play();
    }
    private int count2 = 0;
    public void lyricsShow(MouseEvent event) {
        count2 +=1;
       if(count2%2 != 0) {
            lyrics2.setVisible(true);
            main.setVisible(false);
            mainAnc.getChildren().remove(lyrics2);
            mainAnc.getChildren().add(lyrics2);
            allPanes.add(lyrics2);
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(2));
            slide.setNode(lyrics2);
            slide.setToY(-610);
            slide.play();
        }
        else {
           main.setVisible(true);
           mainAnc.getChildren().remove(lyrics2);
           mainAnc.getChildren().add(lyrics2);
           pages.add(lyrics2);
           TranslateTransition slide = new TranslateTransition();
           slide.setDuration(Duration.seconds(2));
           slide.setNode(lyrics2);
           slide.setToY(610);
           slide.play();
       }

    }

    public void addMPlaylist(MouseEvent event) throws FreeAccountLimitException {
        errorBtn2.setVisible(false);
        playlistLimit1.setVisible(false);
        addBtn.setDisable(false);
        mainAnc.getChildren().remove(adding);
        mainAnc.getChildren().add(adding);
        adding.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(adding);
        slide.setToY(-650);
        slide.play();
    }

    @FXML
    AnchorPane adding;
    @FXML private TableView tree;

    public void closeAdd(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(adding);
        slide.setToY(650);
        slide.play();
    }

    public void creating(MouseEvent event) {
        System.out.println(getDataBase().getAudioFiles().get(0).getTitle());
        System.out.println(getDataBase().getAudioFiles().get(0).getId());
        boolean limit = false;
        System.out.println(musics.getId());
        System.out.println(getDataBase().getAudioFiles().size());
        AddToPlaylistController.getListenerController().setAccount((Listener) account);
        try {
            if (musics != null)
                System.out.println(AddToPlaylistController.getListenerController().addToPlaylist(playlistName2.getText(), musics.getId()));
        } catch (FreeAccountLimitException ex) {
            limit = true;
            System.out.println(ex.getMessage());
            errorBtn2.setVisible(true);
            playlistLimit1.setVisible(true);
            playlistLimit1.setText("you have reached your limit");
            addBtn.setDisable(true);
        } catch (AddedBefore | NonExistedPlaylst e) {
            limit = true;
            System.out.println(e.getMessage());
            errorBtn2.setVisible(true);
            playlistLimit1.setVisible(true);
            playlistLimit1.setText(e.getMessage());
            addBtn.setDisable(true);
        }
        playlistName2.setText(null);
        if (!limit) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(2));
            slide.setNode(adding);
            slide.setToY(650);
            playlistSet();
            slide.play();
        }
    }

    @FXML
    private AnchorPane pers;
    @FXML
    private VBox overviewVbox;
    @FXML private HBox inf;
    @FXML
    private TextField reportBox;

    public void aboutC(MouseEvent event) {
        pages.add(pers);
        allPanes.add(pers);
        pers.setVisible(true);
        overviewVbox.setVisible(false);
    }

    public void overviewC(MouseEvent event) {
        allPanes.add(overviewVbox);
        pages.add(overviewVbox);
        pers.setVisible(false);
        overviewVbox.setVisible(true);
    }

    public void closeR(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(premiumResult);
        slide.setToY(500);
        slide.play();
    }

    public void choose(MouseEvent event) {
        r1t =false; p1t= false; j1t= false; t1t=false; hipt=false; c1t=false; soci1t=false; intert=false; histort=false;

    }

    public void previous(MouseEvent event) {
        BackController.getBackController(allPanes).backTo();
    }

    @FXML private Label resLabel;
    @FXML private Label dashboard;
    @FXML private HBox results;
    @FXML private AnchorPane main1;

    public void search(MouseEvent event) {
        ArrayList res = null;
        tf1.setText(null);
        allPanes.add(main1);
        main1.setVisible(true);
        try {
            results.getChildren().clear();
            res = SearchController.getSearchController().search(tf1.getText());
            resLabel.setText("Results");
            results.getChildren().removeAll();
            resLabel.setVisible(true);
        } catch (NotFoundAudio e) {
            resLabel.setText("There is no such Audio nor Artist");
        }
        try {
            if (res != null) {
                for (Object mostLikedSong : res) {
                    res = SearchController.getSearchController().search(tf1.getText());
                    if (mostLikedSong instanceof Audio) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                        VBox vBox = fxmlLoader.load();
                        SongController songController = fxmlLoader.getController();
                        songController.setInfo((Audio) mostLikedSong);
                        title.setText(((Audio) mostLikedSong).getTitle());
                        songController.play.setOnMouseClicked(event2 ->
                        {
                            ((Audio) mostLikedSong).setPlays(((Audio) mostLikedSong).getPlays() + 1);
                            title.setText(((Audio) mostLikedSong).getTitle());
                            imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(((Audio) mostLikedSong).getCover())).toExternalForm()));
                            artistv.setText(((Audio) mostLikedSong).getArtist());
                            Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(((Audio) mostLikedSong).getCover())).toExternalForm());
                            lyrics.setText(((Music) mostLikedSong).getLyrics());
                            coverFlow.setImage(pause5);
                            if(mediaPlayer != null)
                                mediaPlayer.stop();
                            media = new Media(((Audio)mostLikedSong).getLink());
                            mediaPlayer = new MediaPlayer(media);
                            try {
                                play_pause(event2);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        results.getChildren().add(vBox);
                    }
                }
            }
            System.out.println(res);

        } catch (IOException | NotFoundAudio e) {
            throw new RuntimeException(e);
        }
        try {
            if (res != null) {
                for (Object mostLikedSong : res) {
                    res = SearchController.getSearchController().search(tf1.getText());
                    if (mostLikedSong instanceof Artist) {
                        FXMLLoader fxmlLoader2 = new FXMLLoader();
                        fxmlLoader2.setLocation(getClass().getResource("artistAcc.fxml"));
                        AnchorPane vBox = fxmlLoader2.load();
                        ArtistAccController artistAccController = fxmlLoader2.getController();
                        artistAccController.showInfo.setOnMouseClicked(event2 ->
                        {
                            followers.setText(String.valueOf(((Artist) mostLikedSong).getFollowers().size()));
                            biography.setText(((Artist) mostLikedSong).getBiography());
                            if (mostLikedSong instanceof Singer) {
                                typeProf.setText("Singer");
                            } else {
                                typeProf.setText("Posdcaster");
                            }
                            usernameArtist.setText(((Artist) mostLikedSong).getUserName());
                            ArrayList<Audio> musics = new ArrayList<>(((Artist) mostLikedSong).getReleases());
                            allPanes.add(sc2);
                            pages.add(sc2);
                            sc2.setVisible(true);
                            try {
                                for (Audio music : musics) {
                                    FXMLLoader fxmlLoader3 = new FXMLLoader();
                                    fxmlLoader3.setLocation(getClass().getResource("song.fxml"));
                                    VBox vBox2 = fxmlLoader3.load();
                                    SongController songController = fxmlLoader3.getController();
                                    songController.setInfo(music);
                                    title.setText(music.getTitle());
                                    songController.play.setOnMouseClicked(event3 ->
                                    {
                                        music.setPlays(music.getPlays() + 1);
                                        title.setText(music.getTitle());
                                        imgv.setImage(new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm()));
                                        artistv.setText(music.getArtist());
                                        Image pause5 = new Image(Objects.requireNonNull(BaseHomeController.class.getResource(music.getCover())).toExternalForm());
                                        lyrics.setText(((Music) music).getLyrics());
                                        coverFlow.setImage(pause5);
                                        if(mediaPlayer != null)
                                            mediaPlayer.stop();
                                        media = new Media(((Audio)mostLikedSong).getLink());
                                        mediaPlayer = new MediaPlayer(media);
                                        try {
                                            play_pause(event2);
                                        } catch (FileNotFoundException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    lastRelease.getChildren().add(vBox2);

                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        artistAccController.setData((Artist) mostLikedSong);
                        title.setText(((Artist) mostLikedSong).getUserName());
                        results.getChildren().add(vBox);
                    }

                }
            }
        } catch (IOException | NotFoundAudio e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private HBox topArtists;
    @FXML private Separator sep;
    @FXML private PieChart pie;

    public void likeS(ActionEvent actionEvent)
    {
        sort.setText("based on likes");
        mostP_L.getChildren().clear();
        mostP_L.getChildren().add(bar);
        mostP_L.getChildren().add(sep);
        analystics.getData().clear();
        mostPSet('L');
    }

    public void playS(ActionEvent actionEvent)
    {
        sort.setText("based on plays");
        mostP_L.getChildren().clear();
        mostP_L.getChildren().add(bar);
        mostP_L.getChildren().add(sep);
        analystics.getData().clear();
        mostPSet('P');
    }
@FXML private AnchorPane repo;
@FXML private AnchorPane adminP;
    public void showRepo(MouseEvent event)
    {
        BoxBlur blur = new BoxBlur();
        adminP.setEffect(blur);
        mainAnc.getChildren().remove(repo);
        adminP.getChildren().remove(repo);
        mainAnc.getChildren().add(repo);
        repo.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(repo);
        slide.setToY(-1150);
        slide.play();
    }
    @FXML private AnchorPane publishP;
    @FXML private AnchorPane artistP1;
    @FXML private ScrollPane sc21;
    public void publish(MouseEvent event)
    {
        BoxBlur blur = new BoxBlur();
        publishP.setVisible(true);
        artistP1.getChildren().remove(publishP);
        mainAnc.getChildren().add(publishP);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(publishP);
        slide.setToY(-550);
        artistP1.setEffect(blur);
        slider.setEffect(blur);
        topBar.setEffect(blur);
        slide.play();
    }
    @FXML private TextField titi;
    @FXML private TextField gen;
    @FXML private TextField coverL;
    @FXML private TextField link;
    @FXML private TextArea cap;
    @FXML private Circle userprofile11;
    @FXML private AnchorPane pInfo1;
    public void release(MouseEvent event)
    {
        if(account instanceof Singer) {
            ArtistController.getArtistController().publish('M',titi.getText(),gen.getText(),cap.getText(),link.getText(),coverL.getText());
        }
        else if(account instanceof Podcaster) {
            ArtistController.getArtistController().publish('P',titi.getText(),gen.getText(),cap.getText(),link.getText(),coverL.getText());
        };
        titi.setText(null);
        gen.setText(null);
        cap.setText(null);
        coverL.setText(null);
        link.setText(null);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(publishP);
        slide.setToY(550);
//        publishP.setVisible(false);
        artistP1.setEffect(null);
        slider.setEffect(null);
        topBar.setEffect(null);
        slide.play();
    }
    public void personal2(MouseEvent event)
    {
        username21.setText(account.getUserName());
        fullname21.setText(account.getFullName());
        password21.setText(account.getPassword());
        email21.setText(account.getEmail());
        number21.setText(account.getPhoneNumber());
        credit21.setText(String.valueOf(((Artist)account).getIncome()));
        pInfo1.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(pInfo1);
        slide.setToY(-650);
        slide.play();
    }

    public void ok1(MouseEvent event)
    {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(2));
        slide.setNode(pInfo1);
        slide.setToY(650);
        slide.play();
    }
    @FXML private Label overview1;
    @FXML private Label about1;
    @FXML private VBox overviewVbox1;
    @FXML private BarChart<String,Integer> chart;
    @FXML private AnchorPane pers1;
    public void overviewC2(MouseEvent event) {
        lastRelease1.getChildren().clear();
        allPanes.add(overviewVbox1);
        pages.add(overviewVbox1);
        pers1.setVisible(false);
        overviewVbox1.setVisible(true);
        ArrayList<Audio> audio = new ArrayList<>(((Artist)account).getReleases());
        try {
            for (Audio mostLikedSong : audio) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setInfo(mostLikedSong);
                title.setText(mostLikedSong.getTitle());
                lastRelease1.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void over2(MouseEvent event) {
        overview1.setStyle("-fx-border-width: 0 0 4 0 ");
    }

    public void view2(MouseEvent event) {
        overview1.setStyle("-fx-border-width: 0 0 0 0 ");
    }
    @FXML private Label fol;
    public void aboutC2(MouseEvent event) {
        chart.getData().clear();
        pages.add(pers1);
        allPanes.add(pers1);
        pers1.setVisible(true);
        artistList21.getChildren().clear();
        overviewVbox1.setVisible(false);
        try {
            if (account instanceof Artist) {
                fol.setText(String.valueOf(((Artist) account).getFollowers().size()) + " Followers");
                for (UserAccount userAccount : ((Artist) account).getFollowers()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("artistAdmin.fxml"));
                    HBox vBox = fxmlLoader.load();
                    ArtistAdminController artistAdminController = fxmlLoader.getController();
                    artistAdminController.user = true;
                    artistAdminController.set(userAccount);
                    artistList21.getChildren().add(vBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XYChart.Series series = new XYChart.Series();
        for (Audio audio : getDataBase().getAudioFiles()) {
            if(audio.getArtist() == account.getUserName()) {
                series.getData().add(new XYChart.Data<>(audio.getTitle(), audio.getPlays()));
                System.out.println(audio.getPlays());
            }
        }
        chart.setLegendVisible(false);
        chart.getData().add(series);
        chart.getXAxis().setAnimated(false);
        chart.getYAxis().setAnimated(false);
    }

    public void aboutE2(MouseEvent event) {
        about1.setStyle("-fx-border-width: 0 0 4 0 ");
    }

    public void aboutEx2(MouseEvent event) {
        about1.setStyle("-fx-border-width: 0 0 0 0 ");
    }

    public void ss(MouseEvent event) {
        allPanes.add(main1);
        main1.setVisible(true);
    }
}