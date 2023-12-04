package app.user;

import app.Admin;
import app.utils.Enums;

import java.util.List;

public class NormalUser extends User {
    private boolean isOnline;
    private static List<User> normalUsers;
    private Enums.PageType currentPage;
    public NormalUser(String username, int age, String city) {
        super(username, age, city);
        this.isOnline = true;
        this.currentPage = Enums.PageType.HOMEPAGE;
        initializeNormalUsers();
    }
    private void initializeNormalUsers() {
        if (normalUsers == null) {
            normalUsers = Admin.getNormalUsers();
        }
    }
    public void switchOnlineStatus() {
        isOnline = !isOnline;
    }
    public boolean isOnline() {
        return isOnline;
    }

    public String printCurrentPage() {
        return switch (currentPage) {
            case HOMEPAGE -> formatHomePage();
            case LIKEDCONTENTPAGE -> formatLikedContentPage();
            case ARTISTPAGE -> formatArtistPage();
            case HOSTPAGE -> formatHostPage();
        };
    }

    private String formatHomePage() {
        return "Liked songs:\n\t[Song1, Song2]\n\nFollowed playlists:\n\t[Playlist1, Playlist2]";
    }

    private String formatLikedContentPage() {
        return "Liked Songs:\n\t[Song1 - Artist1, Song2 - Artist2]\n\nFollowed Playlists:\n\t[Playlist1 - Owner1, Playlist2 - Owner2]";
    }

    private String formatArtistPage() {
        return "Albums:\n\t[Album1, Album2]\n\nMerch:\n\t[Merch1 - Price1: Description1, Merch2 - Price2: Description2]\n\nEvents:\n\t[Event1 - Date1: Description1, Event2 - Date2: Description2]";
    }

    private String formatHostPage() {
        return "Podcasts:\n\t[Podcast1: [Episode1 - Description1, Episode2 - Description2]]\n\nAnnouncements\n\t[Announcement1 - Description1, Announcement2 - Description2]";
    }

    public void setCurrentPage(Enums.PageType currentPage) {
        this.currentPage = currentPage;
    }

    public Enums.PageType getCurrentPage() {
        return currentPage;
    }
}
