package app;

import app.user.artist.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.artist.Artist;
import app.user.host.Host;
import app.user.User;
import fileio.input.*;

import java.util.*;
import java.util.stream.Collectors;

public class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
    private static int timestamp = 0;

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static Artist getArtist(String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for(User user: users) {
            if(user.getOnlineStatus()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        timestamp = 0;
    }
    public static List<User> getNormalUsers() {
        return new ArrayList<>(users);
    }
    public static List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }
    public static List<Host> getHosts() {
        return new ArrayList<>(hosts);
    }
    public static String addUser(CommandInput commandInput) {
        String username = commandInput.getUsername();
        if (getUser(username) != null) {
            return "The username " + username + " is already taken.";
        }
        String type = commandInput.getType();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        switch (type) {
            case "user" -> users.add(new User(username, age, city));
            case "artist" -> artists.add(new Artist(username, age, city));
            case "host" -> hosts.add(new Host(username, age, city));
        }
        return "The username " + username + " has been added successfully.";
    }

    public static List<Map<String, Object>> showAlbums(String artistUsername) {
        Artist artist = getArtist(artistUsername);
        if (artist == null) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> albumsInfo = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            Map<String, Object> albumInfo = new HashMap<>();
            albumInfo.put("name", album.getName());
            List<String> songNames = album.getSongs().stream()
                    .map(Song::getName)
                    .collect(Collectors.toList());
            albumInfo.put("songs", songNames);
            albumsInfo.add(albumInfo);
        }

        return albumsInfo;
    }

}
