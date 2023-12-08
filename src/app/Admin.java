package app;

import app.audio.LibraryEntry;
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
    private static final List<Artist> artists = new ArrayList<>();
    private static final List<Host> hosts = new ArrayList<>();
    private static int timestamp = 0;
    private static final Set<String> usernamesInCurrentTest = new HashSet<>();

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
    public static Host getHost(String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }
    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for (Artist artist : artists) {
            albums.addAll(artist.getAlbums());
        }
        return albums;
    }
    public static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }

        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
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
        for (Artist artist : getArtists()) {
            for (Album album : artist.getAlbums()) {
                sortedSongs.addAll(album.getSongs());
            }
        }
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
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalLikes).reversed()
                .thenComparing(Album::getName));
        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= 5) break;
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;

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
    public static List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        for (User user : users) {
            allUsers.add(user.getUsername());
        }
        for (Artist artist : artists) {
            allUsers.add(artist.getUsername());
        }
        for (Host host : hosts) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
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
        User existingUser = getUser(username);
        if(usernamesInCurrentTest.contains(username)) {
            return "The username " + username + " is already taken.";
        }
        if (existingUser != null) {
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
        usernamesInCurrentTest.add(username);
        return "The username " + username + " has been added successfully.";
    }

    public static String deleteUser(CommandInput commandInput) {
        String username = commandInput.getUsername();
        User userToDelete = findUser(username);
        if (userToDelete == null) {
            return "The username " + username + " doesn't exist.";
        }

        List<LibraryEntry> associatedEntries = getAssociatedEntries(userToDelete);
        if (isAnyUserInteractingWith(associatedEntries)) {
            return username + " can't be deleted.";
        }

        removeUser(userToDelete);
        return username + " was successfully deleted.";
    }

    public static List<LibraryEntry> getAssociatedEntries(User user) {
        List<LibraryEntry> associatedEntries = new ArrayList<>();
        if (user instanceof Artist artist) {
            for (Album album : artist.getAlbums()) {
                associatedEntries.add(album);
                associatedEntries.addAll(album.getSongs());
            }
        } else if (user instanceof Host host) {
            associatedEntries.addAll(host.getPodcasts());
        }
        return associatedEntries;
    }

    public static boolean isAnyUserInteractingWith(List<LibraryEntry> entries) {
        for (String username : getAllUsers()) {
            User user = findUser(username);
            if (user != null && user.isInteractingWith(entries)) {
                return true;
            }
        }
        return false;
    }

    public static void removeUser(User user) {
        if (user instanceof Artist artist) {
            artist.clearAlbums();
            for (Album album : artist.getAlbums()) {
                removeAlbum(album);
            }
            artists.remove(artist);

            removeArtistSongsFromLikedSongs(artist);
        } else if (user instanceof Host host) {
            host.clearPodcasts();
            for (Podcast podcast : host.getPodcasts()) {
                removePodcast(podcast);
            }
            hosts.remove(host);
        } else {
            removeUserPlaylists(user);
            users.remove(user);
        }
        usernamesInCurrentTest.remove(user.getUsername());
    }

    private static void removeAlbum(Album album) {
        for (Song song : album.getSongs()) {
            songs.remove(song);
        }
    }

    private static void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    private static void removeArtistSongsFromLikedSongs(Artist artist) {
        for (User user : users) {
            user.getLikedSongs().removeIf(song -> song.getArtist().equals(artist.getUsername()));
        }
    }

    private static void removeUserPlaylists(User user) {
        for (User u : users) {
            u.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().equals(user.getUsername()));
        }
    }
    public static String removeAlbum(CommandInput commandInput) {
        String username = commandInput.getUsername();
        Artist artist = (Artist) findUser(username);
        if (artist == null) {
            return username + " is not an artist.";
        }
        String albumName = commandInput.getName();
        Album albumToRemove = findAlbum(artist, albumName);
        if (albumToRemove == null) {
            return username + " doesn't have an album with the given name.";
        }
        List<LibraryEntry> associatedEntries = getAssociatedEntries(artist);
        if (isAnyUserInteractingWith(associatedEntries)) {
            return username + " can't delete this album.";
        }
        removeAlbum(albumToRemove);

        return username + " deleted the album successfully.";

    }
    public static Album findAlbum(Artist artist, String albumName) {
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
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
    public static List<Map<String, Object>> showPodcasts(String hostUsername) {
        Host host = getHost(hostUsername);
        if (host == null) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> podcastsInfo = new ArrayList<>();
        for (Podcast podcast : host.getPodcasts()) {
            Map<String, Object> podcastInfo = new HashMap<>();
            podcastInfo.put("name", podcast.getName());
            List<String> episodeNames = podcast.getEpisodes().stream()
                    .map(Episode::getName).toList();
            podcastInfo.put("episodes", episodeNames);
            podcastsInfo.add(podcastInfo);
        }
        return podcastsInfo;
    }

    public static void reset() {
        users.clear();
        songs.clear();
        podcasts.clear();
        artists.clear();
        hosts.clear();
        timestamp = 0;
        usernamesInCurrentTest.clear();
        for (Artist artist : artists) {
            artist.clearAlbums();
        }
        for (Host host : hosts) {
            host.clearPodcasts();
        }
    }
}
