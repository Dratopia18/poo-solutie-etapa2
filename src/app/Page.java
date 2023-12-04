package app;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.List;
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
}
