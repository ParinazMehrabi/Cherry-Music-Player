package org.example.graphic.controller;

import org.example.graphic.exceptions.InvalidFormatException;
import org.example.graphic.exceptions.NotAvailableId;
import org.example.graphic.exceptions.UserNotFoundException;
import org.example.graphic.exceptions.WrongPaswordException;
import org.example.graphic.model.UserAccount;
import org.example.graphic.model.user.Admin;
import org.example.graphic.model.user.artist.Artist;
import org.example.graphic.model.user.artist.Podcaster;
import org.example.graphic.model.user.artist.Singer;
import org.example.graphic.model.user.listener.FreeListener;
import org.example.graphic.model.user.listener.Listener;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.graphic.controller.AdminController.getAdminController;
import static org.example.graphic.controller.ArtistController.getArtistController;
import static org.example.graphic.controller.ListenerController.getListenerController;
import static org.example.graphic.model.DataBase.getDataBase;
import static org.example.graphic.model.user.Admin.getAdmin;

public class UserController {
    private static UserController userController;

        private UserAccount account;

    public UserController() {
    }

    public static UserController getUserController() {
        if (userController == null)
            userController = new UserController();
        return userController;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public String signup(char type, String userName, String password, String fullName, String email, String phoneNumber, String birthDate, String biography) throws ParseException, InvalidFormatException, ParseException, UserNotFoundException {
            UserAccount userAccount = null;
            boolean f = false;
            if (!checkNumberValidation(phoneNumber)) {
                throw new InvalidFormatException("phone number is invalid");
            }
            if (!checkEmailValidation(email)) {
                throw new InvalidFormatException("email address is invalid");

            }
            for (UserAccount user : getDataBase().getUsers()) {
                if (user.getUserName().equals(userName)) {
                    f = true;
                    System.out.println("this id is not available");
                }
            }
            String s = checkPasswordStrength(password);
            if(!f) {
                switch (type) {
                    case 'L' -> {
                        userAccount = new FreeListener(userName, password, fullName, email, phoneNumber, birthDate);
                        ((Listener) userAccount).setAccountCredit(50);
                        getListenerController().setAccount((Listener) userAccount);
                    }
                    case 'S' -> {
                        userAccount = new Singer(userName, password, fullName, email, phoneNumber, birthDate, biography);
                        getArtistController().setAccount((Singer) userAccount);
                    }
                    case 'P' -> {
                        userAccount = new Podcaster(userName, password, fullName, email, phoneNumber, birthDate, biography);
                        getArtistController().setAccount((Podcaster) userAccount);
                    }
                }
                setAccount(userAccount);
                getDataBase().getUsers().add(userAccount);
                return "\033[1;40;31m" + s + "\033[1;40;97m" + "\nyou signed up successfully";
            }
            return "no";
    }
    public static boolean checkNumberValidation(String phoneNumber)
    {
        String numberRegex = "^[0][9][0-9][0-9]{8,8}$";
        Pattern pattern1 = Pattern.compile(numberRegex);
        Matcher matcher1 = pattern1.matcher(phoneNumber);
        return matcher1.matches();
    }
    public static boolean checkEmailValidation(String email)
    {
        String emailRegex = "(?!^[.+&'_-]*@.*$)(^[_\\w\\d+&'-]+(\\.[_\\w\\d+&'-]*)*@[\\w\\d-]+(\\.[\\w\\d-]+)*\\.(([\\d]{1,3})|([\\w]{2,}))$)";
        Pattern pattern2 = Pattern.compile(emailRegex);
        Matcher matcher2 = pattern2.matcher(email);
        return matcher2.matches();
    }
    public static String checkPasswordStrength(String password) {
        if (password.matches("\"(?=.*[!@#$%^/:;<=>?_*.A-Za-z0-9]).*\"")) {
            return "your password is strong";
        } else if (password.matches("(?=.*[0-9]|[A-Za-z]).*") | password.matches("(?=.*[!@#$%^&*.]).*|[A-Za-z]") | (password.matches("(?=.*[!@#$%^&*.]).*|[0-9]"))) {
            return "your password is weak";
        }
        return null;
    }
//    public void listenerSignUp(UserAccount userAccount, String userName, String password, String fullName, String email, String phoneNumber, String birthDate) throws ParseException {
//        userAccount = new FreeListener(userName, password, fullName, email, phoneNumber, birthDate);
//        ((Listener) userAccount).setAccountCredit(50);
//        getListenerController().setAccount((Listener) userAccount);
//    }
//    public void singerSignUp(UserAccount userAccount, String userName, String password, String fullName, String email, String phoneNumber, String birthDate,String biography) throws ParseException
//    {
//        userAccount = new Singer(userName, password, fullName, email, phoneNumber, birthDate, biography);
//        getArtistController().setAccount((Singer) userAccount);
//    }
//    public void podcasterSignUp(UserAccount userAccount, String userName, String password, String fullName, String email, String phoneNumber, String birthDate,String biography) throws ParseException
//    {
//        userAccount = new Podcaster(userName, password, fullName, email, phoneNumber, birthDate, biography);
//        getArtistController().setAccount((Podcaster) userAccount);
//    }
public String login(String userName,String password) throws UserNotFoundException, WrongPaswordException {
    for(UserAccount userAccount : getDataBase().getUsers())
    {
        boolean truth = false;
        boolean truth2 = false;
        for (UserAccount user : getDataBase().getUsers()) {
            if (user.getUserName().equals(userName))
            {
                truth = true;
                if(user.getPassword().equals(password))
                {
                    truth2 = true;
                    userAccount = user;
                }
            }
        }
        if (truth == false) {
            throw new UserNotFoundException();
        }
        if (truth2 == false)
        {
            throw new WrongPaswordException();
        }
        setAccount(userAccount);
        if (userAccount instanceof Listener)
        {
            return listenerLogin(userAccount);
        }
        if (userAccount instanceof Artist)
        {
            return artistLogin(userAccount);
        }
        else if(password.equals(getAdmin().getPassword()) && userName.equals(getAdmin().getUserName()))
        {
            return adminLogin(userAccount);
        }

    }
    return null;
}
    public String listenerLogin(UserAccount userAccount)
    {
        getListenerController().login((Listener) userAccount);
        return "you(Listener) logged in to your account successfully";
    }
    public String artistLogin(UserAccount userAccount)
    {
        getArtistController().login((Artist) userAccount);
        return "you(Artist) logged in to your account successfully";
    }
    public String adminLogin(UserAccount userAccount)
    {
        getAdminController().login((Admin) account);
        return "you(Admin) logged in to your account successfully";
    }
    public String checkAccountInfo(UserAccount userAccount,String userName,String password)
    {
        boolean truth = false;
        boolean truth2 = false;
        for (UserAccount user : getDataBase().getUsers()) {
            if (user.getUserName().equals(userName))
            {
                truth = true;
                if(user.getPassword().equals(password))
                {
                    truth2 = true;
                    userAccount = user;
                }
            }
        }
        if (truth == false) {
            return "this username doesnt exist";
        }
        if (truth2 == false)
        {
            return "password is incorrect";
        }
        return "null";
    }

    public String logout()
    {
        if(account instanceof Artist)
        {
            getArtistController().setAccount(null);
        }
        else if(account instanceof Listener)
        {
            getListenerController().setAccount(null);
        }
        else if(account instanceof Admin)
        {
            getAdminController().setAdmin(null);
        }
        account = null;
        return "you logged out";
    }
    public String followArtists(String userName)
    {
        for(UserAccount userAccount : getDataBase().getUsers())
        {
            if(userAccount.getUserName().equals(userName))
            {
                ((Listener)account).getFollowings().add((Artist) userAccount);
                ((Artist) userAccount).getFollowers().add(getAccount());
                return "you started following " + userName;
            }
        }
        return "username is not valid";
    }
    public StringBuilder help()
    {
        StringBuilder orders = new StringBuilder();
        orders.append(".Signup: \nfor Listeners -> Signup-L-[username]-[password]-[name]-[email]-[phone number]-[birth date]\nfor singers -> Signup-S-[username]-[password]-[name]-[email]-[phone number]-[birth date]-[bio]\nfor podcasters -> Signup-P-[username]-[password]-[name]-[email]-[phone number]-[birth date]-[bio]\n.choose favouriteGenres -> FavouriteGenres-[favourite genres separated with comma(,)]\n.login -> Login-[username]-[password]\n.logout -> Logout\n.see accountInfo -> AccountInfo\n");
        orders.append("Listener commands:\n.GetSuggestions ->GetSuggestions-n\n.Artists -> Artists\n.Artist -> Artist-[username]\n.Follow -> Follow-[username]\n.Search -> Search-[artist name OR audio’s title]\n.Sort:\nbased on likes -> Sort-L\nbased on plays -> Sort-P\n.Filter: \nfilter by artist -> Filter-A-[Artist's username]\nfilter by Genre -> Filter-G-[selected genre]\nfilter by Date -> Filter-D-[Date in (yyyy-MM-dd) format]\nfilter between two dates -> BetweenDates[date1-date2]\n.Add playlist -> Add-[playlist’s name]-[audio’s ID]\n.ShowPlaylists -> ShowPlaylists\n.Show selected playlist information -> SelectPlaylist-[playlist’s name]\n.Play audio -> Play-[audio’s ID]\n.Like audio -> Like-[audio’s ID]\n.show Lyric/caption of an audio -> Lyric-[audio’s ID]\n.create new playlist -> NewPlaylist-[playlist’s name]\n.see followings list -> Followings\n.report an artist -> Report-[artist’s username]-[explanation]\n.increase account credit -> IncreaseCredit-[value]\n.purchase premium -> GetPremium-[package]\n");
        orders.append("Admin commands: \n.audio files sorted by likes -> Statistics\n.see all audio files -> Audios\n.see selected audio file -> Audio-[audio’s ID]\n.see all artists -> Artists\n.see selected artist -> Artist-[username]\n.see all reports -> Reports-[username]\n");
        orders.append(".Artists commands: \n.see followers -> Followers\n.see all musics' play times -> ViewsStatistics\nsee all podcasts' play times -> Statistics\n.show income -> CalculateEarnings\n.release new album -> NewAlbum-[name]\n.Publish: \npublish new music -> Publish-M-[title]-[genre]-[lyric|caption]-[link]-[cover]-[album ID]\npublish new podcast -> Publish-P-[title]-[genre]-[lyric|caption]-[link]-[cover]");
        return orders;
    }
    public String showUserInfo()
    {
        return account.toString();
    }
}
