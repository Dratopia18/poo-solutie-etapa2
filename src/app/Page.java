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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Page {
    public static String generateHomePage(User user) {
        List<Song> likedSongs = user.getLikedSongs();
        List<Playlist> followedPlaylists = user.getFollowedPlaylists();
        return "Liked songs:\n\t[" +
                likedSongs.stream().map(Song::getName).collect(Collectors.joining(", ")) +
                "]\n\nFollowed playlists:\n\t[" +
                followedPlaylists.stream().map(Playlist::getName).collect(Collectors.joining(", "))
                + "]";
    }
    public static String generateLikedContentPage(User user) {
        List<Song> likedSongs = user.getLikedSongs();
        List<Playlist> followedPlaylists = user.getFollowedPlaylists();
        return "Liked songs:\n\t" +
                likedSongs.stream().map(song -> song.getName() + " - " + song.getArtist())
                        .collect(Collectors.joining(", ")) +
                "\n\nFollowed playlists:\n\t" +
                followedPlaylists.stream().map(playlist -> playlist.getName() + " - " + playlist.getOwner())
                        .collect(Collectors.joining(", "));
    }
    public static String generateArtistPage(Artist artist) {
        Set<Album> albums = artist.getAlbums();
        Set<Merch> merchandise = artist.getMerchandise();
        Set<Event> events = artist.getEvents();
        return "Albums:\n\t[" +
                albums.stream().map(Album::getName).collect(Collectors.joining(", ")) +
                "]\n\nMerch:\n\t[" +
                merchandise.stream().map(merch -> merch.getName() + " - " + merch.getPrice()
                                + ":\n\t" + merch.getDescription())
                        .collect(Collectors.joining(", ")) +
                "]\n\nEvents:\n\t[" +
                events.stream().map(event -> event.getName() + " - " + event.getDate()
                                + ":\n\t" + event.getDescription())
                        .collect(Collectors.joining(", ")) + "]";

    }
    public static String generateHostPage(Host host) {
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
