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

public class Page {
    private static final int maxNumber = 5;
    public static String generateHomePage(final User user) {
        List<Song> likedSongsCopy = new ArrayList<>(user.getLikedSongs());
        List<Playlist> followedPlaylistsCopy = new ArrayList<>(user.getFollowedPlaylists());

        likedSongsCopy.sort(Comparator.comparing(Song::getLikesCount).reversed());
        followedPlaylistsCopy.sort(Comparator.comparing(Playlist::getTotalLikesCount).reversed());

        StringBuilder homePageMessage = new StringBuilder("Liked songs:\n\t[");

        int count = Math.min(likedSongsCopy.size(), maxNumber);
        for (int i = 0; i < count; i++) {
            homePageMessage.append(likedSongsCopy.get(i).getName()).append(", ");
        }
        if (!likedSongsCopy.isEmpty()) {
            homePageMessage.setLength(homePageMessage.length() - 2);
        }
        homePageMessage.append("]\n\nFollowed playlists:\n\t[");

        count = Math.min(followedPlaylistsCopy.size(), maxNumber);
        for (int i = 0; i < count; i++) {
            homePageMessage.append(followedPlaylistsCopy.get(i).getName()).append(", ");
        }
        if (!followedPlaylistsCopy.isEmpty()) {
            homePageMessage.setLength(homePageMessage.length() - 2);
        }
        homePageMessage.append("]");

        return homePageMessage.toString();
    }

    public static String generateLikedContentPage(final User user) {
        List<Song> likedSongs = user.getLikedSongs();
        StringBuilder likedSongsMessage = new StringBuilder("Liked songs:\n\t[");

        for (Song song : likedSongs) {
            likedSongsMessage.append(song.getName()).append(" - ")
                    .append(song.getArtist()).append(", ");
        }

        if (!likedSongs.isEmpty()) {
            likedSongsMessage.setLength(likedSongsMessage.length() - 2);
        }

        likedSongsMessage.append("]\n\nFollowed playlists:\n\t[");

        List<Playlist> followedPlaylists = user.getFollowedPlaylists();
        for (Playlist playlist : followedPlaylists) {
            likedSongsMessage.append(playlist.getName()).append(" - ")
                    .append(playlist.getOwner()).append(", ");
        }

        if (!followedPlaylists.isEmpty()) {
            likedSongsMessage.setLength(likedSongsMessage.length() - 2);
        }

        likedSongsMessage.append("]");

        return likedSongsMessage.toString();
    }

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
