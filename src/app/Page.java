package app;

import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.User;
import app.user.artist.Album;
import app.user.artist.Artist;
import app.user.artist.Event;
import app.user.artist.Merch;
import app.user.host.Announcement;
import app.user.host.Host;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Page {
    private static final int MAX_NUMBER = 5;
    private Page() {
    }

    /**
     * Genereaza pagina de start a aplicatiei pentru user
     * @param user userul pentru care se genereaza pagina
     * @return pagina de start a aplicatiei pentru user
     */
    public static String generateHomePage(final User user) {
        List<Song> likedSongsCopy = new ArrayList<>(user.getLikedSongs());
        List<Playlist> followedPlaylistsCopy = new ArrayList<>(user.getFollowedPlaylists());

        likedSongsCopy.sort(Comparator.comparing(Song::getLikesCount).reversed());
        followedPlaylistsCopy.sort(Comparator.comparing(Playlist::getTotalLikesCount).reversed());

        StringBuilder homePageMessage = new StringBuilder("Liked songs:\n\t[");

        int count = Math.min(likedSongsCopy.size(), MAX_NUMBER);
        for (int i = 0; i < count; i++) {
            homePageMessage.append(likedSongsCopy.get(i).getName()).append(", ");
        }
        if (!likedSongsCopy.isEmpty()) {
            homePageMessage.setLength(homePageMessage.length() - 2);
        }
        homePageMessage.append("]\n\nFollowed playlists:\n\t[");

        count = Math.min(followedPlaylistsCopy.size(), MAX_NUMBER);
        for (int i = 0; i < count; i++) {
            homePageMessage.append(followedPlaylistsCopy.get(i).getName()).append(", ");
        }
        if (!followedPlaylistsCopy.isEmpty()) {
            homePageMessage.setLength(homePageMessage.length() - 2);
        }
        homePageMessage.append("]");

        return homePageMessage.toString();
    }

    /**
     * Genereaza pagina cu melodiile liked si playlisturile urmarite de user
     * @param user userul pentru care se genereaza pagina
     * @return pagina cu melodiile liked si playlisturile urmarite de user
     */
    public static String generateLikedContentPage(final User user) {
        List<Song> likedSongs = user.getLikedSongs();
        List<Playlist> followedPlaylists = user.getFollowedPlaylists();
        StringBuilder likedContentPageMessage = new StringBuilder("Liked songs:\n\t[");

        for (Song song : likedSongs) {
            likedContentPageMessage.append(song.getName()).append(" - ")
                    .append(song.getArtist()).append(", ");
        }

        if (!likedSongs.isEmpty()) {
            likedContentPageMessage.setLength(likedContentPageMessage.length() - 2);
        }

        likedContentPageMessage.append("]\n\nFollowed playlists:\n\t[");


        for (Playlist playlist : followedPlaylists) {
            likedContentPageMessage.append(playlist.getName()).append(" - ")
                    .append(playlist.getOwner()).append(", ");
        }

        if (!followedPlaylists.isEmpty()) {
            likedContentPageMessage.setLength(likedContentPageMessage.length() - 2);
        }

        likedContentPageMessage.append("]");

        return likedContentPageMessage.toString();
    }

    /**
     * Genereaza pagina pentru artistul cautat de user
     * @param artist artistul cautat de user
     * @return pagina pentru artistul cautat de user
     */
    public static String generateArtistPage(final Artist artist) {
        Set<Album> albums = artist.getAlbums();
        Set<Merch> merchandise = artist.getMerchandise();
        Set<Event> events = artist.getEvents();
        return "Albums:\n\t["
                + albums.stream().map(Album::getName).collect(Collectors.joining(", "))
                + "]\n\nMerch:\n\t["
                + merchandise.stream().map(merch -> merch.getName() + " - " + merch.getPrice()
                        + ":\n\t" + merch.getDescription())
                        .collect(Collectors.joining(", "))
                + "]\n\nEvents:\n\t["
                + events.stream().map(event -> event.getName() + " - " + event.getDate()
                                + ":\n\t" + event.getDescription())
                        .collect(Collectors.joining(", ")) + "]";

    }

    /**
     * Genereaza pagina pentru hostul cautat de user
     * @param host hostul cautat de user
     * @return pagina pentru hostul cautat de user
     */
    public static String generateHostPage(final Host host) {
        Set<Podcast> podcasts = host.getPodcasts();
        Set<Announcement> announcements = host.getAnnouncements();
        StringBuilder hostPage = new StringBuilder("Podcasts:\n\t[");
        for (Podcast podcast : podcasts) {
            hostPage.append(podcast.getName()).append(":\n\t[");
            for (Episode episode : podcast.getEpisodes()) {
                hostPage.append(episode.getName()).append(" - ")
                        .append(episode.getDescription()).append(", ");
            }
            hostPage.deleteCharAt(hostPage.length() - 2);
            hostPage.deleteCharAt(hostPage.length() - 1);
            hostPage.append("]\n, ");
        }
        hostPage.deleteCharAt(hostPage.length() - 2);
        hostPage.deleteCharAt(hostPage.length() - 1);
        hostPage.append("]\n\n");
        hostPage.append("Announcements:\n");
        for (Announcement announcement : announcements) {
            hostPage.append("\t[").append(announcement.getName()).append(":\n\t")
                    .append(announcement.getDescription());
        }
        hostPage.append("\n]");
        return hostPage.toString();
    }
}
