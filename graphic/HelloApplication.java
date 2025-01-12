package org.example.graphic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.graphic.controller.*;
import org.example.graphic.exceptions.InvalidFormatException;
import org.example.graphic.exceptions.UserNotFoundException;
import org.example.graphic.model.DataBase;
import org.example.graphic.model.audio.Audio;
import org.example.graphic.model.audio.Music;
import org.example.graphic.model.audio.Podcast;
import org.example.graphic.model.user.Admin;
import org.example.graphic.model.user.artist.Artist;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
public class HelloApplication extends Application {
    public static ArrayList<Audio> topMusic= new ArrayList<>();
    public static ArrayList<Audio> suggestions= new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("baseHome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cherry!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws ParseException, UserNotFoundException, InvalidFormatException {
        Admin.getAdmin("Admin","123","Parinaz Mehrabi","Parinaz@6527gmail.com","09025006528","12/12/1990");
        try {
            Music music = new Music("sparks", "cold play", "POP", "https://ts16.tarafdari.com/contents/user858451/content-sound/04._sparks.mp3", "Shared Image.png", "Did I drive you away?\n" +
                    "I know what you'll say\n" +
                    "You say, \"Oh, sing one we know\"\n" +
                    "But I promise you this\n" +
                    "I'll always look out for you\n" +
                    "Yeah, that's what I'll do\n" +
                    "I say, \"Ohh\"\n" +
                    "I say, \"Ohh\"\n" +
                    "My heart is yours\n" +
                    "It's you that I hold on to\n" +
                    "Yeah, that's what I do\n" +

                    "And I know, I was wrong\n" +
                    "But I won't let you down\n" +
                    "Oh, yeah, yeah, yeah, I will, yes, I will\n" +
                    "I said, \"Ohh\"\n" +
                    "I cry, \"Ohh\"\n" +
                    "Yeah, I saw sparks\n" +
                    "Yeah, I saw sparks\n" +
                    "And I saw sparks\n" +
                    "Yeah, I saw sparks\n" +
                    "Sing it out\n");
            topMusic.add(music);
            SignupController.getSignupController().signup('L', "hey", "124", "singer", "singer@gmail.com", "09123456789", "12/12/1990", null);
            ListenerController.getListenerController().addToFavouriteGenres(new String[]{"POP","ROCK","JAZZ","COUNTRY"});
            UserController.getUserController().signup('S', "thprnz2", "Singerone#1", "singer", "singer@gmail.com", "09123456789", "12/12/1990", null);
            UserController.getUserController().signup('S', "singer 1", "Singerone#1", "singer", "singer@gmail.com", "09123456789", "12/12/1990", null);
            UserController.getUserController().signup('S', "coldplay", "Singerone#1", "singer", "singer@gmail.com", "09123456789", "12/12/1990", "Hi there\n its me");
            ArtistController.getArtistController().setAccount((Artist) DataBase.getDataBase().getUsers().get(2));
            ((Artist) DataBase.getDataBase().getUsers().get(2)).getFollowers().add(DataBase.getDataBase().getUsers().get(1));
            ArtistController.getArtistController().calculateEarning();
            SignupController.getSignupController().signup('L', "theprnz", "124", "singer", "singer@gmail.com", "09123456789", "12/12/1990", null);
            ArtistController.getArtistController().newAlbum("here");
            ArtistController.getArtistController().setAccount((Artist) DataBase.getDataBase().getUsers().get(2));
            ArtistController.getArtistController().publish('M',"hello","POP","please be friend with me","https://dl.musicsbaran.ir/music/2022-03/beabadoobee%20-%20The%20Moon%20Song.mp3","moon.jpeg");
            ArtistController.getArtistController().publish('M',"hey","POP","please be friend with me","https://dl.musicsbaran.ir/music/2022-03/beabadoobee%20-%20The%20Moon%20Song.mp3","Be-My-Mistake.jpg");
            ((Artist) DataBase.getDataBase().getUsers().get(2)).getReleases().get(0).setLikes(20);
            DataBase.getDataBase().getAudioFiles().get(1).setPlays(20);
            DataBase.getDataBase().getAudioFiles().get(3).setPlays(10);
            DataBase.getDataBase().getAudioFiles().get(2).setPlays(20);
            Music music2 = new Music("the moon song", "beabadoobеe", "POP", "https://dl.musicsbaran.ir/music/2022-03/beabadoobee%20-%20The%20Moon%20Song.mp3", "moon.jpeg", "I'm lying on the moon\n" +
                    "My dear, I’ll be there soon\n" +
                    "It's a quiet, starry place\n" +
                    "Time's were swallowed\n" +
                    "In space, we’re here a million miles away\n" +
                    "\n" +
                    "There's things I wish I knew\n" +
                    "There's no thing I'd keep from you\n" +
                    "It's a dark and shiny place\n" +
                    "But with you my dear\n" +
                    "I'm safe and we're a million miles away\n" +
                    "\n" +
                    "We're lying on the moon\n" +
                    "It’s a perfect afternoon\n" +
                    "Your shadow follows me all day\n" +
                    "Making sure that I’m\n" +
                    "Okay and we're a million miles away");
            Music music7 = new Music("space song", "beach house", "POP", "https://ts13.tarafdari.com/contents/user763441/content-sound/beach_house_-_space_song.mp3", "Space-Song-Beach-House.jpg", "It was late at night\n" +
                    "You held on tight\n" +
                    "From an empty seat\n" +
                    "A flash of light\n" +
                    "\n" +
                    "It will take a while\n" +
                    "To make you smile\n" +
                    "Somewhere in these eyes\n" +
                    "I'm on your side\n" +
                    "\n" +
                    "You wide-eyed girls\n" +
                    "You get it right\n" +
                    "\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "\n" +
                    "Tender is the night\n" +
                    "For a broken heart\n" +
                    "Who will dry your eyes\n" +
                    "When it falls apart?\n" +
                    "\n" +
                    "What makes this fragile world go 'round?\n" +
                    "Were you ever lost?\n" +
                    "Was she ever found?\n" +
                    "\n" +
                    "Somewhere in these eyes\n" +
                    "\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into place\n" +
                    "Fall back into");
            music7.setLikes(20);
            Music music3 = new Music("cry", "cigarettes after", "INTERVIEW", "https://ts13.tarafdari.com/contents/user760078/content-sound/87292276.mp3", "7f53e3ec9752c0f901d9d1370b569507.1000x1000x1.jpg", "It's making you cry every time\n" +
                    "You give your love to me this way\n" +
                    "Saying you'd wait for me to stay\n" +
                    "I know it hurts you\n" +
                    "But I need to tell you something\n" +
                    "My heart just can't be faithful for long\n" +
                    "I swear I'll only make you cry\n" +
                    "Maybe I'd change for you someday\n" +
                    "But I can't help the way I feel\n" +
                    "Wish I was good, wish that I could\n" +
                    "Give you my love now\n" +
                    "But I need to tell you something\n" +
                    "My heart just can't be faithful for long\n" +
                    "I swear I'll only make you cry\n" +
                    "I need to tell you something\n" +
                    "My heart just can't be faithful for long\n" +
                    "I swear I'll only make you cry\n");
            Music music5 = new Music("neyaz", "Fereydoon Forooghi", "JAZZ", "https://irsv.upmusics.com/AliBZ/Freydon%20Froughi%20-%20Niyaz%20(320).mp3", "ff.jpeg", "تن تو ظهر تابستونو بیادم میاره\n" +
                    "رنگ چشمهای تو بارونو بیادم میاره\n" +
                    "وقتی نیستی زندگی فرقی با زندون نداره\n" +
                    "قهر تو تلخی زندونو بیادم میاره\n" +
                    "من نیازم(نمازم)تو رو هر روز دیدنه\n" +
                    "از لبت دوست دارم شنیدنه\n" +
                    "تو بزرگی مثل اون لحظه که بارون میزنه\n" +
                    " دانلود آهنگ نیاز فریدون فروغی\n" +
                    "تو همون خونی که هر لحظه تو رگهای منه\n" +
                    "تو مثل خواب گل سرخی لطیفی مثل خواب\n" +
                    "من همونم که اگه بی تو باشه جون میکنه\n" +
                    "من نیازم (نمازم)تو رو هر روز دیدنه\n" +
                    "از لبت دوست دارم شنیدنه\n" +
                    "تو مثل وسوسه شکار یک شاپرکی\n" +
                    "تو مثل شوق رها کردن یک بادبادکی\n" +
                    "تو همیشه مثل یک قصه پر از حادثه ای\n");
            Music music22 = new Music("Be My Mistake", "the 1975", "JAZZ", "https://ts2.tarafdari.com/contents/user242396/content-sound/06_be_my_mistake.mp3", "Be-My-Mistake.jpg", "And be my mistake\n" +
                    "And turn out the light\n" +
                    "She bought me those jeans\n" +
                    "The ones you like\n" +
                    "I don't wanna hug\n" +
                    "I just wanna sleep\n" +
                    "The smell of your hair\n" +
                    "Reminds me of her feet\n" +
                    "So don't wait outside my hotel room\n" +
                    "Just wait 'til I give you a sign\n" +
                    "'Cause I get lonesome sometimes\n" +
                    "Save all the jokes you're gonna make\n" +
                    "While I see how much drink I can take\n" +
                    "Then be my mistake\n" +
                    "I shouldn't have called\n" +
                    "'Cause we shouldn't speak\n" +
                    "You do make me hard\n" +
                    "But she makes me weak\n" +
                    "And don't wait outside my hotel room\n" +
                    "Just wait 'til I give you a sign\n" +
                    "'Cause I get lonesome sometimes\n" +
                    "Save all the jokes you're gonna make\n" +
                    "While I see how much drink I can take\n" +
                    "Then be my mistake");
            Music music6 = new Music("fourth of july", "Sufjan Stevens", "JAZZ", "https://ts12.tarafdari.com/contents/user742612/content-sound/sufjan_stevens_-_fourth_of_july.mp3", "images-2.jpeg", "The evil it spread like a fever ahead\n" +
                    "\n" +
                    "It was night when you died, my firefly\n" +
                    "\n" +
                    "What could I have said to raise you from the dead?\n" +
                    "\n" +
                    "Oh could I be the sky on the Fourth of July?\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "Well you do enough talk\n" +
                    "\n" +
                    "My little hawk, why do you cry?\n" +
                    "\n" +
                    "Tell me what did you learn from the Tillamook burn?\n" +
                    "\n" +
                    "Or the Fourth of July?\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "Sitting at the bed with the halo at your head\n" +
                    "\n" +
                    "Was it all a disguise, like Junior High\n" +
                    "\n" +
                    "Where everything was fiction, future, and prediction\n" +
                    "\n" +
                    "Now, where am I?\n" +
                    "\n" +
                    "My fading supply\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "Did you get enough love, my little dove\n" +
                    "\n" +
                    "Why do you cry?\n" +
                    "\n" +
                    "And I'm sorry I left, but it was for the best\n" +
                    "\n" +
                    "Though it never felt right\n" +
                    "\n" +
                    "My little Versailles\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "The hospital asked should the body be cast\n" +
                    "\n" +
                    "Before I say goodbye, my star in the sky\n" +
                    "\n" +
                    "Such a funny thought to wrap you up in cloth\n" +
                    "\n" +
                    "Do you find it all right, my dragonfly?\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "Shall we look at the moon, my little loon\n" +
                    "\n" +
                    "Why do you cry?\n" +
                    "\n" +
                    "Make the most of your life, while it is rife\n" +
                    "\n" +
                    "While it is light\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "Well you do enough talk\n" +
                    "\n" +
                    "My little hawk, why do you cry?\n" +
                    "\n" +
                    "Tell me what did you learn from the Tillamook burn?\n" +
                    "\n" +
                    "Or the Fourth of July?\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    " \n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n" +
                    "We're all gonna die\n" +
                    "\n");

            topMusic.add(music3);
            topMusic.add(music5);
            topMusic.add(music2);
            topMusic.add(music6);
            topMusic.add(music7);
            topMusic.add(new Music("each night", "Tamino", "Jazz", "https://ts4.tarafdari.com/contents/user398626/content-sound/108344028.mp3", "tam_ep_laab_cover.jpg", "[Verse 1]\n" +
                    "On this precious evening, quiet and still\n" +
                    "Pale stars shine upon a bed of dark red flowers\n" +
                    "It suits them like it suits us\n" +
                    "\n" +
                    "[Verse 2]\n" +
                    "It's where I start believing in a world for me\n" +
                    "An atmosphere still enveloped by young love\n" +
                    "And all that it moves by\n" +
                    "\n" +
                    "[Pre-Chorus]\n" +
                    "It's there you'll find me, stretched out in the fields\n" +
                    "Hope you don't mind me, staying where I please\n" +
                    "\n" +
                    "[Chorus]\n" +
                    "To seal this beauty\n" +
                    "It ain't real till it’s sure\n" +
                    "Let me feel the thrill of it\n" +
                    "Each time again\n" +
                    "\n" +
                    "[Verse 3]\n" +
                    "By a kind deceiving I am shown only what is pure\n" +
                    "How can it endure, how can it last some more\n" +
                    "\n" +
                    "[Verse 4]\n" +
                    "In this perfect circle, I've come to know the face of my defeat\n" +
                    "It's always worth repeating\n" +
                    "Somehow it is worth it"));

            Music music4 = new Music("heaven knows im", "Smiths", "ROCK", "https://ts1.tarafdari.com/contents/user152204/content-sound/09_-_heaven_knows_im_miserable_now.mp3", "jpeg.jpeg","I was happy in the haze of a drunken hour\n" +
                    "But heaven knows I'm miserable now\n" +
                    "I was looking for a job, and then I found a job\n" +
                    "And heaven knows I'm miserable now\n" +
                    "\n" +
                    "In my life\n" +
                    "Why do I give valuable time\n" +
                    "To people who don't care if I live or die\n" +
                    "\n" +
                    "Two lovers entwined pass me by\n" +
                    "And heaven knows I'm miserable now\n" +
                    "I was looking for a job, and then I found a job\n" +
                    "And heaven knows I'm miserable now\n" +
                    "\n" +
                    "In my life\n" +
                    "Why do I give valuable time\n" +
                    "To people who don't care if I live or die\n" +
                    "\n" +
                    "What she asked of me at the end of the day\n" +
                    "Caligula would have blushed\n" +
                    "\"You've been in the house too long\" she said\n" +
                    "And I naturally fled\n" +
                    "\n" +
                    "In my life\n" +
                    "Why do I smile\n" +
                    "At people who I'd much rather kick in the eye\n" +
                    "\n" +
                    "I was happy in the haze of a drunken hour\n" +
                    "But heaven knows I'm miserable now\n" +
                    "\"You've been in the house too long\" she said\n" +
                    "And I naturally fled\n" +
                    "\n" +
                    "In my life\n" +
                    "Why do I give valuable time\n" +
                    "To people who don't care if I live or die\n" +
                    "\n");
            Podcast music1 = new Podcast("Swan lake", "Tchaikovsky", "POP", "https://ts7.tarafdari.com/contents/user6984/content-sound/cd2_-_04_-_tchaikovsky_-_swan_lake_swan_theme.mp3", "Tchaikovsky-Swan-Lake-Music-fa.com_.jpg","");
            Podcast music10 = new Podcast("corpse bride", "Danny Elfman", "POP", "https://ts15.tarafdari.com/contents/user823957/content-sound/45.-the-piano-duet.mp3", "tumblr_260cc267aa67832df230556f2a52c225_931e5db6_540-2.jpg","");
            Music music11 = new Music("m.", "Anıl Emre Daldal", "ROCK", "https://ts16.tarafdari.com/contents/user846551/content-sound/m._anil_emre_daldal.mp3", "M.jpeg","İlk geceden belliydi\n" +
                    "O zaman ner'deydin?\n" +
                    "Dün daha seni öperken\n" +
                    "Şimdi buna, \"Dur\" diyemezsin\n" +
                    "Sözlerin, gözlerin\n" +
                    "Ellerin yalnız benim için\n" +
                    "Düşlerim, gülüşlerim\n" +
                    "Hayallerim yalnız senin için\n" +
                    "Seni bulamamışken ben kayboluyorum\n" +
                    "Şimdi gökyüzünde\n" +
                    "Kendimi ellerinle dans ederken buluyorum\n" +
                    "Al beni yanına, sevgilim\n" +
                    "Seni bana geri ver, sevgilim\n" +
                    "N'olur geri dön, sevgilim\n" +
                    "Seni bana geri ver, sevgilim, mm\n" +
                    "Al beni yanına, sevgilim\n" +
                    "Seni bana geri ver, sevgilim\n" +
                    "N'olur geri dön, sevgilim\n" +
                    "Seni bana geri ver, sevgilim");
            topMusic.add(music1);
            topMusic.add(music10);
            topMusic.add(music11);
//            suggestions.add(new Music("Indigo night", "Tamino", "POP", "https://ts12.tarafdari.com/contents/user677085/content-sound/tamino_-_each_time.mp3", "6420.jpg", "[Verse 1]\n" +
//                    "On this precious evening, quiet and still\n" +
//                    "Pale stars shine upon a bed of dark red flowers\n" +
//                    "It suits them like it suits us\n" +
//                    "\n" +
//                    "[Verse 2]\n" +
//                    "It's where I start believing in a world for me\n" +
//                    "An atmosphere still enveloped by young love\n" +
//                    "And all that it moves by\n" +
//                    "\n" +
//                    "[Pre-Chorus]\n" +
//                    "It's there you'll find me, stretched out in the fields\n" +
//                    "Hope you don't mind me, staying where I please\n" +
//                    "\n" +
//                    "[Chorus]\n" +
//                    "To seal this beauty\n" +
//                    "It ain't real till it’s sure\n" +
//                    "Let me feel the thrill of it\n" +
//                    "Each time again\n" +
//                    "\n" +
//                    "[Verse 3]\n" +
//                    "By a kind deceiving I am shown only what is pure\n" +
//                    "How can it endure, how can it last some more\n" +
//                    "\n" +
//                    "[Verse 4]\n" +
//                    "In this perfect circle, I've come to know the face of my defeat\n" +
//                    "It's always worth repeating\n" +
//                    "Somehow it is worth it"));
            SortController sortController = new SortController();
            topMusic = sortController.sort(topMusic);
            System.out.println("c".compareTo("z"));
            music1.setLikes(10);
            music2.setLikes(30);
            music3.setLikes(40);
            music4.setLikes(30);
            music5.setLikes(50);
            music1.setPlays(10);
            music2.setPlays(20);
            music3.setPlays(40);
            music4.setPlays(50);
            music5.setPlays(10);
            ListenerController.getListenerController().recordReport("coldplay","i hate you");
            System.out.println(ListenerController.getListenerController().filterAudioFiles('G',"POP").size());
            launch();
        } finally {
            System.out.println("Have a good day");
        }
    }
}